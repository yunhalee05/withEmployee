import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {Link} from 'react-router-dom'
import { logout } from '../_actions/authActions'

function Header() {

    const auth = useSelector(state => state.auth)

    const dispatch = useDispatch()

    const handleLogout= () =>{
        dispatch(logout())
    }


    return (
        <div className="header">
            <div className="brand-name">
                With Employee
            </div>

            <div className="header-menu">
                <div className="header-company">
                    <Link to="/company">Company</Link>
                </div>
                <div className="header-team">
                    <Link to="/team">Team</Link>
                </div>
                <div className="header-users">
                    <Link to="/users">Users</Link>
                </div>

                <div className="dropdown">
                    <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        {auth.user.name}
                    </button>
                    <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <Link className="dropdown-item" to={`/user/${auth.user.id}`}>Profile</Link>
                        <Link className="dropdown-item" to={`/user/edit/${auth.user.id}`}>Edit Profile</Link>
                        <Link className="dropdown-item" to={`/teams/${auth.user.id}`}>My Teams</Link>
                    </div>
                </div>

                <div className="header-logout">
                    <button onClick={handleLogout}>Logout</button>
                </div>
            </div>
        </div>
    )
}

export default Header
