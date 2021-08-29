import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import { createConversation, getConversations } from '../_actions/conversationActions'

function ConversationCard({users, setConversationId}) {

    const [search, setSearch] = useState('')
    const [searchUser, setSearchUser] = useState([])

    const [conversations, setconversations] = useState([])

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getConversations()).then(res=>
            setconversations(res)
            )
    }, [dispatch])

    const handleSubmit = (e) =>{
        e.preventDefault()
        const find = users.filter(u=>u.name.includes(search))
        setSearchUser(find)
    }

    const handleAddUser=(user)=>{
        const existinguser = []

        console.log(conversations)
        conversations.forEach(conversation=>{
            if(conversation.users.length===1){
                conversation.users.forEach(u=>{
                    if(u.id===user.id){
                        existinguser.push(u);
                    }
                })
            }

        })

        if(existinguser.length ===0){
            dispatch(createConversation(user.email)).then(res=>
                setConversationId(res.id)
            )
        }

        


    }
    return (
        <div className="conversations">

            <form className="search-input" onSubmit={handleSubmit}>
                <input type="text" value={search} placeholder="Enter to search member" onChange={e=>setSearch(e.target.value)} />
                <button type="submit" style={{display:'none'}}>Search</button>
            </form>
            <div className="conversation-list">
                {
                    searchUser.length>0 &&
                    searchUser.map(user=>(
                        <div className="message-user" onClick={()=>handleAddUser(user)} key={user.id}>
                            {user.name}
                        </div>
                    ))
                }
            </div>

            <div>
                {
                    conversations.map(conversation=>(
                        <div key={conversation.id} onClick={()=>setConversationId(conversation.id)}>
                            {conversation.id}
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default ConversationCard
