import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getteamlist } from '../_actions/teamActions'

function TeamListScreen() {

    const teamlist = useSelector(state => state.teamlist)
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getteamlist())
    }, [dispatch])
    return (
        <div className="list">
            <div className="list-name">
                TeamList
            </div>
            <div className="list-table">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Company</th>
                            <th>Total Member</th>

                        </tr>
                    </thead>

                    <tbody>
                        {
                            teamlist.teams.map((team,index)=>(
                                <tr key={index}>
                                    <td>{team.name}</td>
                                    <td>{team.company}</td>
                                    <td>{team.users.length}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            
        </div>
    )
}

export default TeamListScreen
