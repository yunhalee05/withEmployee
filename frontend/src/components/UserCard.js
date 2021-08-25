import React from 'react'
import { useDispatch } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import { deleteuserteam } from '../_actions/userActions'
import {Link} from 'react-router-dom'

function UserCard({user, teamId, setCeos, setLeaders, setMembers}) {

    const userId = user.id
    const dispatch = useDispatch()

    const handleDelete = () =>{
        if(window.confirm("Are you sure to exclude this member?")){
            dispatch(deleteuserteam({userId, teamId})).then(res=>{
                const id = res
                dispatch(getteam({id})).then(res=>{
                    setCeos(res.users.filter(user=>user.role==="CEO"))
                    setLeaders(res.users.filter(user=>user.role==="Leader"))
                    setMembers(res.users.filter(user=>user.role==="Member"))
                })
            })
        }

    }
    return (
        <div className="user-card">
            {
                teamId &&
                <div className="company-delete-button">
                    <button onClick={handleDelete}>DELETE</button>
                </div>
            }

            <div className="user-name">
                <Link to={`/user/${user.id}`}>{user.name}</Link>
            </div>
            <div className="user-role">
                {user.role}
            </div>
            <div className="user-email">
                <span>E-mail : </span>
                <span>{user.email}</span>
            </div>
            <div className="user-phoneNumber">
                <span>Phone : </span>
                <span>{user.phoneNumber}</span>
            </div>
        </div>
    )
}

export default UserCard
