import React from 'react'

function MessageCard({id}) {
    return (
        <div className="message">
            <div className="message-container">
                container
            </div>

            {console.log(id)}


            <div className="message-input">
                message-input
            </div>
        </div>
    )
}

export default MessageCard
