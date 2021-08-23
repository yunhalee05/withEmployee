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
                <Link to="/home">With Employee</Link>
            </div>

            <div className="header-menu">

                <div className="dropdown dropdown-toggle">
                    <span>List</span>
                    <div className="dropdown-menu dropdown-menu-right" >
                        <Link className="dropdown-item" to="/company">Company List</Link>
                        <Link className="dropdown-item" to="/team">Teasm List</Link>
                        <Link className="dropdown-item" to="/users">User List</Link>
                    </div>
                </div>

                <div className="dropdown dropdown-toggle">
                    <span >
                        {auth.user.name}
                    </span>
                    <div className="dropdown-menu dropdown-menu-right" >
                        <Link className="dropdown-item" to={`/user/${auth.user.id}`}>Profile</Link>
                        {/* <Link className="dropdown-item" to={`/user/edit/${auth.user.id}`}>Edit Profile</Link> */}
                        <Link className="dropdown-item" to={`/teams/${auth.user.id}`}>My Teams</Link>
                        {
                            auth.user.role ==="CEO" &&
                            <Link className="dropdown-item" to={`/companies/${auth.user.id}`}>My Companies</Link>
                        }
                        <div>
                            <button onClick={handleLogout}>Logout</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Header
