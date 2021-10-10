import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import AddTeamModal from '../components/AddTeamModal'
import { getcompany } from '../_actions/companyActions'
import {Link} from 'react-router-dom'
import { deleteteam } from '../_actions/teamActions'
import ConversationUserCard from '../components/messages/ConversationUserCard'
import ConversationCard from '../components/messages/ConversationCard'
import MessageCard from '../components/messages/MessageCard'


function CompanyScreen(props) {

    const id = props.match.params.id

    const company = useSelector(state => state.company)
    const auth = useSelector(state => state.auth)

    const [addTeam, setAddTeam] = useState(false)
    const [editTeam, setEditTeam] = useState(false)

    const [showConversation, setShowConversation] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getcompany({id}))
    }, [dispatch])

    const handleDelete= (teamId) =>{
        if(window.confirm("Are you sure to delete this team ? After this action, users related to this team are also deleted.")){
            dispatch(deleteteam({teamId})).then(res=>{
                // dispatch(getcompany({id}))
            })
        }
    }

    const [conversation, setConversation] = useState({})


    return (
        
        <div className="user-team">
            {
                company.loading===false &&company.company &&
                <div>
                <div className="company-screen">
                    <div className="company-screen-name">
                        <div>{company.company.name}</div>
                    </div>

                    <div className="company-screen-ceo">
                        <div>{company.company.ceo.name}</div>
                        <div>{company.company.ceo.email}</div>
                    </div>

                    <div className="company-screen-description">
                        <div>{company.company.description}</div>
                    </div>
                </div>

                {
                    (auth.user.teams.filter(t=>t.company===company.company.name).length ===1 ||company.company.ceo.id ===auth.user.id)&&
                    <div className="showconversation-button" style={{marginTop:"10rem"}}>
                        <button onClick={()=>setShowConversation(!showConversation)}>
                            {
                                showConversation
                                ? 'Hide Company Talk'
                                : 'Show Company Talk'
                            }
                        </button>
                    </div>
                }
                    
                {
                    showConversation &&
                    <div className="messages">
                        <ConversationCard users={company.company.members} setConversation={setConversation} conversation={conversation} belongTo="Company"/>
                        <MessageCard conversation={conversation} setConversation={setConversation}  />
                    </div>
                }

                {company.company.teams && 
                    <div className="company-info-line" style={{marginTop:"10rem"}}>
                        <span>&nbsp;{company.company.teams.length} TEAMS IN THIS COMPANY&nbsp;</span>
                    </div>
                }

            
                <div className="team-card-container">
                    {
                        company.company.teams.map((team, index)=>(
                            <div>
                            <div className="team-card" key={index}>
                                {
                                    company.company.ceo.id===auth.user.id &&
                                    <div className="team-button">
                                        <i className="far fa-edit" onClick={()=>setEditTeam(!editTeam)}></i>
                                        <i className="far fa-trash-alt" onClick={()=>handleDelete(team.id)}></i>
                                    </div>
                                }

                                <div style={{display:'flex',  justifyContent:"space-around", alignItems:"center"}}>
                                    <div className="total-number">
                                        <div>{team.totalNumber}</div>
                                        <div style={{fontSize:"9px"}}>Members  </div>
                                    </div>
                                    {
                                        (auth.user.teams.filter(t=>t.id ===team.id).length===1 || company.company.ceo.id ===auth.user.id )
                                        ?<Link to={`/team/${team.id}`}>
                                            <div className="team-name">
                                                {team.name}
                                            </div>
                                        </Link>
                                        :<div className="team-name">
                                            {team.name}
                                        </div>
                                    }
                                </div>

                            </div>
                                {
                                    editTeam &&
                                    <AddTeamModal team={team} companyId={id} setAddTeam={setEditTeam} />
                                }
                            </div>
                        ))
                    }

                    {
                        company.company.ceo.id === auth.user.id &&
                        <div className="team-add-button" onClick={()=>setAddTeam(!addTeam)}>
                            <i class="far fa-plus-square fa-2x"></i>
                            <div>ADD TEAM</div>
                        </div>
                    }
                </div>

                {company.company.members && 
                    <div className="company-info-line" style={{marginTop:"10rem"}} >
                        <span>&nbsp;{company.company.members.length} MEMBERS WORKING NOW &nbsp; </span>
                    </div>
                }

                <div className="user-card-container" style={{marginBottom:"10rem"}}>
                    {
                        company.company.members.map((user, index)=>(
                            <div className="user-card" key={index}>
                                <div className="user-info-container">
                                    <div className={`user-role ${user.role==='CEO'? 'ceo': user.role==="Leader"? 'leader':''}`}>
                                        {user.role}
                                    </div>
                                    <div className="user-name">
                                        {user.name}
                                    </div>
                                </div>
                            </div>
                        ))
                        
                    }
                </div>
                </div>
            }

            {
                addTeam &&
                <AddTeamModal companyId={id} setAddTeam={setAddTeam} />
            }


        </div>
    )
}

export default CompanyScreen
