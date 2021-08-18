import React from 'react'
import {Link} from 'react-router-dom'

function Header() {


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
                <div className="header-user">
                    <Link to="/profile">User</Link>
                </div>
                <div className="header-login">
                    <Link to="/login">Login</Link>
                </div>
                <div className="header-logout">
                    {/* <Link>Logout</Link> */}
                </div>
            </div>
        </div>
    )
}

export default Header
