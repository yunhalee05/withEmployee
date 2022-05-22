import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { createConversation } from '../../_actions/conversationActions'
import { createMessage, getMessages } from '../../_actions/messageActions'
import Display from './Display'
import usersIcon from '../../images/users.svg'
import userIcon from '../../images/user.svg'
import messageImage from '../../images/message.png'
import Error from '../Error'
import Loading from '../Loading'



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
    
            const result = await axios.post('/messages/image', bodyFormData,{
                headers : {Authorization : `Bearer ${auth.token}`}
            })
            imageURL = result.data
        }


        if(conversation.id==="new"){
            const userEmails = []
            conversation.users.forEach(user=> userEmails.push(user.email))

            const conversationRequest={
                text:text,
                imageUrl:imageURL,
                teamMember:conversation.isTeamMember,
                sameCompany:conversation.isSameCompany,
                otherCompany:conversation.isOtherCompany,
                userEmails: [...userEmails, auth.user.email]
            }

            dispatch(createConversation(conversationRequest)).then(res=>
                {
                    const messageRequest={
                        content:text,
                        imageUrl:imageURL,
                        conversationId:res.id,
                        userId:auth.user.id
                    }

                    dispatch(createMessage(messageRequest, res)).then(response=>
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
                message.error && <Error error ={message.error}/>
            }
            {
                message.loading && <Loading/>
            }
            {
                !conversation.id &&
                <div className="no-message">
                    <img src={messageImage} alt="" />
                    {/* <i className="far fa-comments fa-8x"></i> */}
                </div>
            }


            {
                conversation.id &&
                (
                <span>
                    <div >
                        {
                            conversation.users.length >1 
                            ? <div className="message-header">
                                <img src={usersIcon} alt="userIconImage" />
                                {
                                    conversation.users.map(user=>(
                                        user.name
                                    ))
                                }
                            </div>
                            : <div className="message-header">
                                <img src={conversation.users[0].imageUrl?conversation.users[0].imageUrl :userIcon} alt="userIconImage" />
                                <span>{conversation.users[0].name}</span>
                            </div>
                        }
                    </div>

                    <div className="message-container" style={{height: imageUrl? 'calc(100% - 180px)':''}}>
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
                        <div id="file_media">
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
                </span>
                )
            }

        </div>
    )
}

export default MessageCard
