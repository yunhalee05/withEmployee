import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import Error from '../components/Error';
import Loading from '../components/Loading';
import TeamCard from '../components/TeamCard';
import { getteams } from '../_actions/teamActions';

function UserTeamScreen(props) {

    const id = props.match.params.id;
    const team = useSelector(state => state.team)

    const dispatch = useDispatch()
    useEffect(() => {
        if( !team.id || team.id != id) {
            dispatch(getteams({id}))
        }
    }, [id])




    return (
        <div className="user-team">
            {
                team.error && <Error error = {team.error}/>
            }
            {
                team.loading && <Loading/>
            } 
            <div className="team-card-container">
                {
                    team.loading===false && team.teams.length >0 && team.teams.map((team, index)=>(
                        
                        <TeamCard team={team} key={index}/>
                    ))
                }
            </div>
            {
                team.teams && team.teams.length ==0 && 
                <div className='profile-userteam-title'>
                    You dont't belong to any team yet.
                </div>
            }
        </div>

    )
}

export default UserTeamScreen
