import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import CompanyConversationCard from '../components/messages/CompanyConversationCard'
import MessageCard from '../components/messages/MessageCard'
import { getcompanylist } from '../_actions/companyActions'
// import ConversationCard from '../components/ConversationCard'

function HomeScreen() {

    const dispatch = useDispatch()

    const [conversation, setConversation] = useState({})
    const [companies, setCompanies] = useState([])

    useEffect(() => {
        dispatch(getcompanylist()).then(res=>{
            setCompanies(res)
        })
    }, [dispatch])

    return (
        <div className="home-screen">
            <div className="messages">
                <CompanyConversationCard companies={companies} setConversation={setConversation} conversation={conversation} belongTo="Other"/>
                <MessageCard conversation={conversation} setConversation={setConversation}  />
            </div>
        </div>
    )
}

export default HomeScreen
