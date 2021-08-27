import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import AddTeamModal from '../components/AddTeamModal'
import { getcompany } from '../_actions/companyActions'
import {Link} from 'react-router-dom'
import { deleteteam } from '../_actions/teamActions'


function CompanyScreen(props) {

    const id = props.match.params.id

    const company = useSelector(state => state.company)

    const [addTeam, setAddTeam] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getcompany({id}))
    }, [dispatch])

    const handleDelete= (teamId) =>{
        if(window.confirm("Are you sure to delete this team ? After this action, users related to this team are also deleted.")){
            dispatch(deleteteam({teamId})).then(res=>{
                dispatch(getcompany({id}))
            })
        }

    }

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

            
                <div className="team-card-container">
                    {
                        company.company.teams.map((team, index)=>(
                            <div className="team-card" key={index}>
                                <div className="company-delete-button">
                                    <button onClick={()=>handleDelete(team.id)}>DELETE</button>
                                </div>
                                <Link to={`/team/${team.id}`}>
                                    <div className="team-name">
                                        {team.name}
                                    </div>
                                </Link>
                                <div className="total-number">
                                    <div>{team.totalNumber}</div>
                                    <div style={{fontSize:"9px"}}>Members  </div>
                                </div>
                            </div>
                        ))
                    }
                    <div className="card-button">
                        <button onClick={()=>setAddTeam(!addTeam)}>ADD TEAM</button>
                    </div>

                </div>


                <div className="user-card-container" >
                    {
                        company.company.members.map((user, index)=>(
                            <div className="user-card" key={index}>
                                <div className="user-name">
                                    {user.name}
                                </div>
                                <div className="user-role">
                                    {user.role}
                                </div>
                                <div className="user-email">
                                    <span>E-mail : </span>
                                    <span>{user.email}</span>
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
