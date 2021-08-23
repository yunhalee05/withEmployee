import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import {Link} from 'react-router-dom'
import UserCard from '../components/UserCard'


function TeamScreen(props) {

    const id = props.match.params.id

    const dispatch = useDispatch()

    const [ceos, setCeos] = useState([])
    const [leaders, setLeaders] = useState([])
    const [members, setMembers] = useState([])

    useEffect(() => {
        dispatch(getteam({id})).then(res=>{
            setCeos(res.users.filter(user=>user.role==="CEO"))
            setLeaders(res.users.filter(user=>user.role==="Leader"))
            setMembers(res.users.filter(user=>user.role==="Member"))
        })


    }, [dispatch])

    const team = useSelector(state => state.team)


    return (
        <div className="user-team">
            {
                team.loading ===false &&
                    <div className="user-card-container" >
                    {
                        ceos.map((user, index)=>(
                            <UserCard user={user} key={index}/>
                        ))
                        
                    }
                    {
                        leaders.map((user, index)=>(
                            <UserCard user={user} key={index}/>
                        ))
                        
                    }
                    {
                        members.map((user, index)=>(
                            <UserCard user={user} key={index}/>
                        ))
                        
                    }
                    </div>
            }
        </div>
    )
}

export default TeamScreen
