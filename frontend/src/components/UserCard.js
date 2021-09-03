import React from 'react'
import { useDispatch } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import { deleteuserteam } from '../_actions/userActions'
import {Link} from 'react-router-dom'
import userIcon from '../images/user.svg'


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
                <div className="user-team-delete-button">
                    <div className={`user-role ${user.role==='CEO'? 'ceo': user.role==="Leader"? 'leader':''}`}>
                        {user.role}
                    </div>
                    <i onClick={handleDelete} className="fas fa-user-minus"></i>
                </div>
            }

            <div className="user-info-container">

                <div className="user-info-image">
                    <img src={user.imageUrl? user.imageUrl : userIcon} alt="image" />
                </div>

                <div className="user-info">

                    <div className="user-name">
                        <Link to={`/user/${user.id}`}>{user.name}</Link>
                    </div>
                    <hr/>
                    <div className="user-email">
                        <span>{user.email}</span>
                    </div>
                    <div className="user-phoneNumber">
                        <span>{user.phoneNumber}</span>
                    </div>
                </div>

            </div>


            
        </div>
    )
}

export default UserCard
