import React from 'react'
import { Redirect, Route } from 'react-router'

function AdminRouter(props) {
    const auth = JSON.parse(localStorage.getItem('auth'))
    return auth.user.role==="Admin"
    ? <Route {...props} />
    : <Redirect to="/"/>
}

export default AdminRouter
