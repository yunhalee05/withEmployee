import { Redirect, Route } from "react-router-dom"
import React from "react"

const PrivateRouter = (props)=>{
    const auth = localStorage.getItem('auth')
    return auth
            ? <Route {...props} />
            : <Redirect to="/login"/>
}

export default PrivateRouter

