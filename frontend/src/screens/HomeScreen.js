import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import CompanyCard from '../components/CompanyCard'
import CompanyConversationCard from '../components/messages/CompanyConversationCard'
import MessageCard from '../components/messages/MessageCard'
import { getcompaniesrecommendation, getcompanylist } from '../_actions/companyActions'

function HomeScreen() {

    const dispatch = useDispatch()

    const [conversation, setConversation] = useState({})
    const [companies, setCompanies] = useState([])

    const companylist = useSelector(state => state.companylist)
    const {recommendation} = companylist

    useEffect(() => {
        dispatch(getcompanylist()).then(res=>{
            setCompanies(res)
        })
        dispatch(getcompaniesrecommendation())
    }, [dispatch])


    const handleRecommendation= () =>{
        dispatch(getcompaniesrecommendation())
    }

    return (
        <div className="home-screen">
            <div className="messages">
                <CompanyConversationCard companies={companies} setConversation={setConversation} conversation={conversation} belongTo="Other"/>
                <MessageCard conversation={conversation} setConversation={setConversation}  />
            </div>

            <div className="home-mention">
                <span>Our Recommendation For you</span>
                <i class="fas fa-sync-alt" onClick={handleRecommendation}></i>
            </div>

            {
                recommendation &&
                <div className="company-card-container">
                {   
                    recommendation.map((company, index)=>(
                        <CompanyCard company={company} key={index}/>
                    ))
                }
                </div>
            }
        </div>
    )
}

export default HomeScreen
