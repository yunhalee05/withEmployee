import React from 'react'
import userIcon from '../../images/user.svg'
import usersIcon from '../../images/users.svg'


function ConversationUserCard({conversation, handleDeleteConversation}) {
    return (
        <div className="search-user d-flex align-items-center justify-content-between">
            {
                conversation.users.length>1 &&
                <div className="d-flex align-items-center" >
                    <img src={usersIcon} alt="image" />
                    <div className="ml-1 pl-1">
                        <span className="d-block">{conversation.users[0].name} and {conversation.users.length}people</span>

                        <small style={{opacity:0.7}}>
                            {
                                conversation.text
                                ? <div>
                                    <div>{conversation.text}</div>
                                    {
                                        conversation.imageUrl &&
                                        <div>
                                            <i className="fas fa-image"></i>
                                        </div>
                                    }
                                </div>
                                : ''
                            }
                        </small>
                    </div>
                </div>
            }
            {
                conversation.users.length ===1 &&
                <div className="d-flex align-items-center" >
                    <img src={conversation.users[0].imageUrl?conversation.users[0].imageUrl :userIcon} alt="image" />
                    <div className="ml-1 pl-1">
                        <span className="d-block">{conversation.users[0].name}</span>
                        <small style={{opacity:0.7}}>
                            {
                                conversation.text
                                ? <div>
                                    <div>{conversation.text}</div>
                                    {
                                        conversation.imageUrl &&
                                        <div>
                                            <i className="fas fa-image"></i>
                                        </div>
                                    }
                                </div>
                                : ''
                            }
                        </small>
                    </div>
                </div>
            }

            {
                conversation.id !=='new' &&
                <span onClick={()=>handleDeleteConversation(conversation)}>&times;</span>
            }
        
        </div>
    )
}

export default ConversationUserCard
