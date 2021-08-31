import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { createConversation, deleteConversation, getConversations } from '../../_actions/conversationActions'
import { ADD_NEWCONVERSATION } from '../../_constants/conversationConstants'

function ConversationCard({users, setConversation}) {

    const auth = useSelector(state => state.auth)

    const [search, setSearch] = useState('')
    const [searchUser, setSearchUser] = useState([])

    const message = useSelector(state => state.message)
    const {conversations} = message


    // const [conversations, setconversations] = useState([])

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getConversations())
        // .then(res=>
        //     setConversations(res.filter(conversation=> conversation.isTeamMember===true))
        //     )
    }, [dispatch])

    const handleSubmit = (e) =>{
        e.preventDefault()
        const find = users.filter(u=>u.name.includes(search)&& u.id !== auth.user.id)
        setSearchUser(find)
    }

    const handleAddUser=(user)=>{
        const existinguser = []

        conversations.forEach(conversation=>{
            if(conversation.users.length===1){
                conversation.users.forEach(u=>{
                    if(u.id===user.id){
                        existinguser.push(u)
                        setConversation(conversation)
                        setSearch('')
                        setSearchUser([])
                    }
                })
            }

        })

        if(existinguser.length ===0){
            const newConversation = {
                id:"new",
                users:[user]
            }

            dispatch({
                type:ADD_NEWCONVERSATION,
                payload:newConversation
            })

            setConversation(newConversation)
            // setConversations([...conversations, newConversation])
            setSearch('')
            setSearchUser([])

        }

    }

    const handleDeleteConversation=(conversation)=>{
        if(window.confirm('Are you sure to delete this conversation? It is irreversible.')){
            dispatch(deleteConversation(conversation))
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
                    conversations && conversations.filter(c=> c.isTeamMember===true || c.id==="new").map(conversation=>(
                        <div key={conversation.id} onClick={()=>setConversation(conversation)}>
                            {conversation.users.map(u=>(
                                u.name
                            ))
                            }
                            <span>{conversation.text}</span>
                            <span onClick={()=>handleDeleteConversation(conversation)}>&times;</span>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default ConversationCard
