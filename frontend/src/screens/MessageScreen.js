import React from 'react'
// import ConversationCard from '../components/ConversationCard'
// import MessageCard from '../components/MessageCard'

function MessageScreen(props) {

    const id = props.match.params.id

    return (
            <div className="messages">
                {/* <ConversationCard />
                <MessageCard/> */}
            </div>
    )
}

export default MessageScreen
