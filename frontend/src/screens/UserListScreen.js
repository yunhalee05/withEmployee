import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { getuserlist } from '../_actions/userActions'
import { useDispatch, useSelector } from 'react-redux'



function UserListScreen() {

    const userlist = useSelector(state => state.userlist)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getuserlist())

    }, [dispatch])

    return (
        <div className="list">
            <div className="list-name">
                UserList
            </div>
            <div className="list-table">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>E-mail</th>
                            <th>Phone</th>
                            <th>Description</th>
                            <th>Team</th>
                            <th>Company</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            userlist.users.map((user,index)=>(
                                <tr key={index}>
                                    <td>{user.name}</td>
                                    <td>{user.email}</td>
                                    <td>{user.phoneNumber}</td>
                                    <td>{user.description}</td>
                                    <td>{user.teams.map(t=>t.name)}</td>
                                    <td>{user.teams.map(t=>t.company)}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            
        </div>
    )
}

export default UserListScreen
