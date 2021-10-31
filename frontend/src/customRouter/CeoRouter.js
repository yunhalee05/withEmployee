import React from 'react'
import { Redirect, Route } from 'react-router'

function CEORouter(props) {
    const auth = JSON.parse(localStorage.getItem('auth'))
    return (auth && (auth.user.role==="CEO" || auth.user.role ==="Admin"))
    ? <Route {...props} />
    : <Redirect to="/"/>
}

export default CEORouter
