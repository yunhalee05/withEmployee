import React from 'react'
import { useDispatch } from 'react-redux'
import {Link} from 'react-router-dom'
import { deleteteam } from '../_actions/teamActions'

function TeamCard({team}) {

    const dispatch = useDispatch()

    const handleDelete= (teamId) =>{
        
        if(window.confirm("Are you sure to delete this team ? After this action, users related to this team are also deleted.")){
            dispatch(deleteteam({teamId}))
        }

    }

    return (
        <div className="team-card">
                <div className="team-name">
                    <div className="team-button">
                        <i className="far fa-edit"></i>
                        <i className="far fa-trash-alt" onClick={()=>handleDelete(team.id)}></i>
                    </div>
                    <Link to={`/team/${team.id}`}>
                    {team.name}
                    </Link>
                </div>
            <div className="team-company">
                <div className="total-number">
                    <div>{team.totalNumber}</div>
                    <div style={{fontSize:"9px"}}>Members  </div>
                </div>
                <Link to={`/company/${team.company.id}`}>
                    <div className="company-name">{team.company.name}</div>
                    <div className="company-ceo">{team.company.ceo}</div>
                </Link>
            </div>
        </div>
    )
}

export default TeamCard
