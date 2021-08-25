import React from 'react'
import { useSelector } from 'react-redux'
import {Link} from 'react-router-dom'

function TeamCard({team}) {

    return (
        <div className="team-card">
            <Link to={`/team/${team.id}`}>
                <div className="team-name">
                    {team.name}
                </div>
            </Link>

            <div className="total-number">
                <div>{team.totalNumber}</div>
                <div style={{fontSize:"9px"}}>Members  </div>
            </div>

            <div className="team-company">
                <Link to={`/company/${team.company.id}`}>
                    <div className="company-name">{team.company.name}</div>
                    <div className="company-ceo">{team.company.ceo}</div>
                </Link>
            </div>
        </div>
    )
}

export default TeamCard
