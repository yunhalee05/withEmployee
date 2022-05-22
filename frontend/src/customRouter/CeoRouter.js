import React from 'react'
import { useSelector } from 'react-redux'
import { Redirect, Route } from 'react-router'

function CEORouter(props) {
    const auth = useSelector(state => state.auth)
    return ((auth.user) && (auth.user.role==="CEO" || auth.user.role ==="Admin"))
    ? <Route {...props} />
    : <Redirect to="/"/>
}

export default CEORouter
