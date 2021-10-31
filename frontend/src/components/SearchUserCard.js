import React from 'react'
import userIcon from '../images/user.svg'


function SearchUserCard({user}) {
    return (
        <div className="search-user">
            <img src={user.imageUrl?user.imageUrl :userIcon} alt="userIconImage" />
            <span style={{padding:"5px"}}>{user.name}</span>
        </div>
    )
}

export default SearchUserCard
