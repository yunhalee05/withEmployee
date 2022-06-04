import React from 'react'
import { Redirect, Route } from 'react-router'

function AdminRouter(props) {
    const auth = useSelector(state => state.auth)
    return auth.user && (auth.user.role==="Admin")
    ? <Route {...props} />
    : <Redirect to="/"/>
}

export default AdminRouter
