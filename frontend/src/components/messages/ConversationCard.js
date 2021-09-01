import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { createConversation, deleteConversation, getConversations } from '../../_actions/conversationActions'
import { ADD_NEWCONVERSATION } from '../../_constants/conversationConstants'
import SearchUserCard from '../SearchUserCard'
import ConversationUserCard from './ConversationUserCard'

function ConversationCard({users, setConversation, conversation}) {

    const auth = useSelector(state => state.auth)

    const [search, setSearch] = useState('')
    const [searchUser, setSearchUser] = useState([])

    const message = useSelector(state => state.message)
    const {conversations} = message

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getConversations())
    }, [dispatch])

    const handleSubmit = (e) =>{
        e.preventDefault()

        if(search===''){
            setSearchUser([])
            setSearch('')
        }else{
            const find = users.filter(u=>u.name.includes(search)&& u.id !== auth.user.id)
            setSearchUser(find)
        }
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
                <input type="text" value={search} placeholder="Search Member" onChange={e=>setSearch(e.target.value)} />
                <button type="submit" style={{display:'none'}}>Search</button>
            </form>

            <div className="conversation-list">
                {
                    searchUser.length>0 ?
                    searchUser.map(user=>(
                        <div onClick={()=>handleAddUser(user)} key={user.id}>
                            <SearchUserCard user={user} />
                        </div>
                    ))
                    :  conversations && conversations.filter(c=> c.isTeamMember===true || c.id==="new").map(c=>(
                        <div className={conversation.id===c.id ? 'active' : ''} key={c.id} onClick={()=>setConversation(c)}>
                            <ConversationUserCard conversation={c} handleDeleteConversation={handleDeleteConversation}/>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default ConversationCard
