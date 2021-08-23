import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import TeamCard from '../components/TeamCard';
import { getteams } from '../_actions/teamActions';

function UserTeamScreen(props) {

    const id = props.match.params.id;

    const dispatch = useDispatch()
    useEffect(() => {
        dispatch(getteams({id}))
    }, [dispatch])


    const team = useSelector(state => state.team)


    return (
        <div className="user-team">

            <div className="team-card-container">
                {
                    team.loading===false && team.teams.map((team, index)=>(
                        
                        <TeamCard team={team} key={index}/>
                    ))
                }
            </div>
        </div>

    )
}

export default UserTeamScreen
