import React, { useEffect } from 'react'
import { useDispatch } from 'react-redux';
import { getteams } from '../_actions/teamActions';

function UserTeamScreen(props) {

    const id = props.match.params.id;

    const dispatch = useDispatch()
    useEffect(() => {
        dispatch(getteams({id}))
    }, [dispatch])
    return (
        <div>
            
        </div>
    )
}

export default UserTeamScreen
