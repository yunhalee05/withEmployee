import React, { useEffect, useState } from 'react'
import axios from 'axios'


function UserListScreen() {

    const [users, setUsers] = useState([])

    const getUsers=async()=>{
        const res = await axios.get('/api/users')
                                
        console.log(res)
        setUsers(res.data._embedded.users)
    }

    const getRole= async(user)=>{
        const res = await axios.get(`${user._links.role.href}`)
        console.log(res)
    }


    useEffect(() => {
        getUsers()
    }, [])
    return (
        <div>
            {
                users.map((user,index)=>(
                    <div key={index}>
                        {getRole(user)}
                    </div>
                ))
            }
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>E-mail</th>
                        <th>Phone</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
            {
                users.map((user,index)=>(
                    <tr key={index}>
                        <td>{user.name}</td>
                        <td>{user.email}</td>
                        <td>{user.phoneNumber}</td>
                        <td>{user.description}</td>
                    </tr>
                ))
            }
            

            </tbody>
            </table>
            
        </div>
    )
}

export default UserListScreen
