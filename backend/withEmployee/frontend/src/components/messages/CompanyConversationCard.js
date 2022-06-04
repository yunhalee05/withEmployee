import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getcompaniessearch } from '../../_actions/companyActions'
import { deleteConversation, getConversations } from '../../_actions/conversationActions'
import { ADD_NEWCONVERSATION } from '../../_constants/conversationConstants'
import ConversationUserCard from './ConversationUserCard'

function CompanyConversationCard({ setConversation, conversation, belongTo}) {

    const [search, setSearch] = useState('')
    const [searchCompany, setSearchCompany] = useState([])

    const message = useSelector(state => state.message)
    const {conversations} = message

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getConversations())
    }, [dispatch])

    const handleSubmit = (e) =>{
        e.preventDefault()

        if(search===''){
            setSearchCompany([])
            setSearch('')
        }else{
            dispatch(getcompaniessearch(search)).then(res=>{
                setSearchCompany(res)
            })
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
                        setSearchCompany([])
                    }
                })
            }

        })

        if(existinguser.length ===0){
            const newConversation = {
                id:"new",
                isTeamMember:belongTo==="Team"? true:false,
                isSameCompany:belongTo==="Company"? true:false,
                isOtherCompany:belongTo==="Other"? true:false,
                users:[user]
            }

            dispatch({
                type:ADD_NEWCONVERSATION,
                payload:newConversation
            })

            setConversation(newConversation)
            setSearch('')
            setSearchCompany([])

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
                <input type="text" value={search} placeholder="Search Company" onChange={e=>setSearch(e.target.value)} />
                <button type="submit" style={{display:'none'}}>Search</button>
            </form>

            <div className="conversation-list">
                {
                    searchCompany.length>0 ?
                    searchCompany.map(company=>(
                        <div onClick={()=>handleAddUser(company.ceo)} key={company.id}>
                            <div className="search-user" style={{display:"flex",justifyContent:"space-between"}}>
                                <div style={{fontSize:"1.1rem", fontWeight:"600"}}>{company.name}</div>
                                <div style={{padding:"5px"}}>{company.ceo.name}</div>
                            </div>
                        </div>
                    ))
                    
                    :  conversations && conversations.filter(c=> c.isOtherCompany===true || c.id==="new").map(c=>(
                        <div className={conversation.id===c.id ? 'active' : ''} key={c.id} onClick={()=>setConversation(c)}>
                            <ConversationUserCard conversation={c} handleDeleteConversation={handleDeleteConversation} belongTo={belongTo}/>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default CompanyConversationCard
