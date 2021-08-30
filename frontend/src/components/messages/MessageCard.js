import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { createMessage, getMessages } from '../../_actions/messageActions'
import Display from './Display'

function MessageCard({conversation}) {

    const auth = useSelector(state => state.auth)

    const [messages, setMessages] = useState([])
    const [text, setText] = useState('')
    const [imageUrl, setImageUrl] = useState('')

    const dispatch = useDispatch()
    useEffect(() => {
        if(conversation.id){
            dispatch(getMessages(conversation.id)).then(res=>
                setMessages(res)    
            )
        }
    }, [conversation])

    const handleSubmit= (e) =>{
        e.preventDefault()

        const messageDTO={
            content:text,
            imageUrl:imageUrl,
            conversationId:conversation.id,
            userId:auth.user.id
        }

        dispatch(createMessage(messageDTO, conversation)).then(res=>
            setMessages([...messages, res])
        )

        setText('')

    }
    return (
        <div className="message">
            {
                conversation!==null &&
                <div className="no-message">
                    no message
                </div>
            }


            {
                conversation !==null &&
                <div>
                <div className="message-header">
                    123
                </div>

                <div className="message-container" >
                    <div className="message-display">
                        {messages &&
                        messages.map(message=>(
                            <div key={message.id}>

                                {
                                    message.user.id !== auth.user.id &&
                                    <div className="message-row other-message">
                                        <Display message={message}/>
                                    </div>
                                }
                                {
                                    message.user.id ===auth.user.id &&
                                    <div className="message-row my-message">
                                        <Display message={message} setMessages={setMessages} messages={messages}/>
                                    </div>
                                }
                            </div>
                        ))}
                    </div>
                </div>

                <form className="message-input" onSubmit={handleSubmit}>
                    <input type="text" placeholder="Enter your Message" value={text} onChange={(e)=>setText(e.target.value)} />

                    <button type="submit" className="material-icons" disabled={text? false : true}> 
                        near_me
                    </button>
                </form>
                </div>
            }

        </div>
    )
}

export default MessageCard
