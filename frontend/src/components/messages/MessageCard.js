import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { createConversation } from '../../_actions/conversationActions'
import { createMessage, getMessages } from '../../_actions/messageActions'
import Display from './Display'

function MessageCard({conversation, setConversation}) {

    const auth = useSelector(state => state.auth)
    const message = useSelector(state => state.message)
    const {messages} = message

    const [text, setText] = useState('')
    const [imageUrl, setImageUrl] = useState('')

    const dispatch = useDispatch()
    useEffect(() => {
        if(conversation.id && conversation.id !== "new"){
            dispatch(getMessages(conversation.id))
        }
    }, [conversation])


    const handleImage = async(e)=>{
        const file = e.target.files[0]
        const err = checkImage(file)

        if(err) return window.alert(err)
        if(file){
            var preview = document.getElementById('preview')
            preview.src = URL.createObjectURL(file)
        }
        setImageUrl(file)
    }

    const checkImage = (file) =>{
        let err=""

        if(!file) return err="File does not exist."
        if(file.size>1024*1024){
            err = "The largest image size is 1mb."
        }
        if(file.type !== 'image/jpeg' && file.type !== 'image/png'){
            err = "Image format is incorrect."
        }

        return err
    }

    const handleImageDelete= () =>{
        setImageUrl('')
        var preview = document.getElementById('preview')
        preview.src = ''
    }


    const handleSubmit= async(e) =>{
        e.preventDefault()

        var imageURL = ''
        if(imageUrl!==''){
            const bodyFormData = new FormData()
            bodyFormData.append('multipartFile', imageUrl)
    
            const result = await axios.post('/message/image', bodyFormData,{
                headers : {Authorization : `Bearer ${auth.token}`}
            })
            imageURL = result.data
        }


        if(conversation.id==="new"){
            const userEmails = []
            conversation.users.forEach(user=> userEmails.push(user.email))

            const conversationListDTO={
                text:text,
                imageUrl:imageURL,
                teamMember:true,
                userEmails: [...userEmails, auth.user.email]
            }

            dispatch(createConversation(conversationListDTO)).then(res=>
                {
                    // setConversations(conversations.map(conversation=> conversation.id==="new" ? res : conversation))
                    const messageDTO={
                        content:text,
                        imageUrl:imageURL,
                        conversationId:res.id,
                        userId:auth.user.id
                    }

                    dispatch(createMessage(messageDTO, res)).then(response=>
                        setConversation(res)
                    )
                }
            )
        }

        else if(conversation.id!=="new"){
            const messageDTO={
                content:text,
                imageUrl:imageURL,
                conversationId:conversation.id,
                userId:auth.user.id
            }
            dispatch(createMessage(messageDTO, conversation))
        }

        setText('')
        var preview = document.getElementById('preview')
        preview.src = ''
        setImageUrl('')

    }
    return (
        <div className="message">
            {
                conversation===null &&
                <div className="no-message">
                    no message
                </div>
            }


            {
                conversation.id &&
                (
                <div>
                    <div className="message-header">
                        {
                            conversation.users.map(user=>(
                                <div key={user.id}>
                                    {user.name}
                                </div>
                            ))
                        }
                    </div>

                    <div className="message-container" >
                        <div className="message-display">
                            { (conversation.id !=="new" && messages) &&
                            messages.map(message=>(
                                <div key={message.id}>

                                    {
                                        message.user.id !== auth.user.id &&
                                        <div className="message-row other-message">
                                            <Display message={message} conversation={conversation}/>
                                        </div>
                                    }
                                    {
                                        message.user.id ===auth.user.id &&
                                        <div className="message-row my-message">
                                            <Display message={message} conversation={conversation}/>
                                        </div>
                                    }
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="show_media" style={{display: imageUrl ? 'grid':'none'}}>
                        <div>
                            <img id="preview" src={''} alt="imageURL" />
                            <span onClick={handleImageDelete}>&times;</span>
                        </div>
                    </div>


                    <form className="message-input" onSubmit={handleSubmit} >
                        <input type="text" placeholder="Enter your Message" value={text} onChange={(e)=>setText(e.target.value)} />

                        <div className="file_upload">
                            <i className="fas fa-image text-danger"></i>
                            <input type="file" name="file" id="file" accept="image/*" onChange={handleImage}/>
                        </div>

                        <button type="submit" className="material-icons" disabled={text || imageUrl? false : true}> 
                            near_me
                        </button>
                    </form>
                </div>
                )
            }

        </div>
    )
}

export default MessageCard
