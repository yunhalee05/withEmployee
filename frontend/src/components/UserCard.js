import React from 'react'

function UserCard({user}) {
    return (
        <div className="user-card">
            <div className="user-name">
                {user.name}
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
