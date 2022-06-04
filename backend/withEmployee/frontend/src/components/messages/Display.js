import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import userIcon from '../../images/user.svg'
import { deleteMessage } from '../../_actions/messageActions'

function Display({message, conversation}) {

    const auth = useSelector(state => state.auth)

    const dispatch = useDispatch()

    const handleDelete= ()=>{
        if(!message) return;
        if(window.confirm('Do you want to delete this message?')){
            dispatch(deleteMessage(message.id, conversation))

        }

    }
    return (
        
        <div>
            
            <div className="message-title">
                <img src={message.user.imageUrl? message.user.imageUrl : userIcon} alt="" />
                <small className="d-block">{message.user.name}</small>
            </div>

            <div className="you-content">
                {
                    message.user.id === auth.user.id &&
                    <i className="fas fa-trash" style={{color:"#858585"}} onClick={handleDelete}></i>
                }

                <div>
                    {
                        message.content &&
                        <div className="message-text">
                            {message.content}
                        </div>
                    }

                    {
                        message.imageUrl &&
                        <div>
                            <img src={message.imageUrl} alt="images" className="image-thumbnail" />
                        </div>
                    }
                </div>


            </div>

            <div className="message-time">
                {new Date(message.createdAt).toLocaleString()}
            </div>
        </div>
    )
}

export default Display
