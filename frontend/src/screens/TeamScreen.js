import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import {Link} from 'react-router-dom'

function TeamScreen(props) {

    const id = props.match.params.id

    const team = useSelector(state => state.team)
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getteam({id}))
    }, [dispatch])

    return (
        <div className="team-screen">
            {
                team.teams.map((t, index)=>(
                    <div className="team-card" key={index}>
                        <div className="team-card-name">
                            {t.name}
                        </div>
                        <div className="team-card-company">
                            <Link to={`/company/${t.company}`}>{t.company}</Link>
                        </div>

                        {
                            t.users.map(user=>(
                                <div key={user.id} className="team-card-user">
                                    <Link to={`/user/${user.id}`}>
                                    <span>{user.name}</span>
                                    <span>{user.phoneNumber}</span>
                                    <span>{user.role}</span>
                                    </Link>
                                </div>
                            ))
                        }
                    </div>

                ))
            }
        </div>
    )
}

export default TeamScreen
