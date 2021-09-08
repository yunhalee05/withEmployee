import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import CompanyCard from '../components/CompanyCard'
import CompanyConversationCard from '../components/messages/CompanyConversationCard'
import MessageCard from '../components/messages/MessageCard'
import { getallcompaniesbypage, getcompaniesrecommendation, getcompanylist } from '../_actions/companyActions'
import { GET_LOAD_MORE_COMPANIES } from '../_constants/companyConstants'

function HomeScreen() {

    const dispatch = useDispatch()


    const [conversation, setConversation] = useState({})

    const [page, setPage] = useState(1)
    const [sort, setSort] = useState("createdAtAsc")

    const companylist = useSelector(state => state.companylist)
    const {recommendation, companies} = companylist
    const auth = useSelector(state => state.auth)

    useEffect(() => {
        dispatch(getcompaniesrecommendation())
    }, [dispatch])

    useEffect(() => {
        dispatch(getallcompaniesbypage(1, sort))
    }, [dispatch, sort])

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
            <div className="messages">
                <CompanyConversationCard setConversation={setConversation} conversation={conversation} belongTo="Other"/>
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

            <div className="home-mention">
                <span>All the Companies</span>
            </div>

            <div className="company-sort">
                Sort by :&nbsp; &nbsp;
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
    )
}

export default HomeScreen
