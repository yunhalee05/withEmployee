import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import CompanyCard from '../components/CompanyCard'
import CompanyConversationCard from '../components/messages/CompanyConversationCard'
import MessageCard from '../components/messages/MessageCard'
import { getallcompaniesbypage, getcompaniesrecommendation } from '../_actions/companyActions'
import { GET_LOAD_MORE_COMPANIES } from '../_constants/companyConstants'
import home from '../images/home.png'
import Loading from '../components/Loading'
import Error from '../components/Error'

function HomeScreen() {

    const dispatch = useDispatch()

    const [conversation, setConversation] = useState({})

    const [page, setPage] = useState(1)
    const [sort, setSort] = useState("createdAtAsc")

    const [showConversation, setShowConversation] = useState(false)

    const companylist = useSelector(state => state.companylist)
    const {recommendation, companies} = companylist
    const auth = useSelector(state => state.auth)

    useEffect(() => {
        if(auth.user){
            dispatch(getcompaniesrecommendation())
        }
    }, [auth.user])

    useEffect(() => {
        if(auth.user){
            dispatch(getallcompaniesbypage(1, sort))
        }
    }, [auth.user, sort])

    const handleRecommendation= () =>{
        dispatch(getcompaniesrecommendation())
    }

    const handleLoadMore = async() =>{
        const res = await axios.get(`/companies?page=${page+1}&sort=${sort}`,{
            headers : {Authorization : `Bearer ${auth.token}`}
        })

        dispatch({
            type:GET_LOAD_MORE_COMPANIES,
            payload:res.data.companies
        })

        setPage(page+1)
    }

    return (
        <div className="home-screen">
            {
                companylist.error && <Error error={companylist.error}/>
            }
            {
                companylist.loading && <Loading/>
            }
            <div className="home-background">
                <div className="home-backgound-title">
                    Looking for company to work with?<br/>
                    <span>&nbsp; &nbsp; &nbsp; &nbsp;Find your help & Team up with other company</span>
                </div>
                <div className="home-backgound-image">
                    <img src={home} alt="" />
                </div>
            </div>
            
            {
                auth.user &&
                <div>
                    <div className="showconversation-button">
                        <button onClick={()=>setShowConversation(!showConversation)}>
                            {
                                showConversation
                                ? 'Hide Company Talk'
                                : 'Show Company Talk'
                            }
                        </button>
                    </div>

                    {
                        showConversation &&
                        <div className="messages">
                            <CompanyConversationCard setConversation={setConversation} conversation={conversation} belongTo="Other"/>
                            <MessageCard conversation={conversation} setConversation={setConversation}  />
                        </div>
                    }

                    <div className="home-recommendation">
                        <div className="home-mention" >
                            <span className="recommendation_button" onClick={handleRecommendation}>🥫</span>
                            <span>Our Recommendations For you</span>
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

                    <div className="home-company">
                    <div className="home-mention">
                        <span>All the Companies</span>
                    </div>

                    <div className="company-sort">
                        SORT BY :&nbsp; &nbsp;
                        <select value={sort} onChange={e=>setSort(e.target.value)}>
                            <option value="createdAtAsc">newest companies</option>
                            <option value="createdAtDesc">classic companies</option>
                            <option value="nameAsc">by name(ASC)</option>
                            <option value="nameDesc">by name(DESC)</option>
                        </select>
                    </div>

                    {
                        companies &&
                        <div>
                            <div className="company-card-container">
                                {
                                    companies.map((company, index)=>(
                                        <CompanyCard company={company} key={index}/>
                                    ))
                                }

                            </div>
                            {
                                companylist.totalPage >page &&
                                <button onClick={handleLoadMore}>load more</button>
                            }
                        </div>
                    }
                    </div>
                </div>
            }
        </div>
    )
}

export default HomeScreen
