import { Redirect, Route } from "react-router-dom"
import React from "react"
import { useSelector } from "react-redux"

const PrivateRouter = (props)=>{
    const auth = useSelector(state => state.auth)
    return auth.user
            ? <Route {...props} />
            : <Redirect to="/login"/>
}

export default PrivateRouter

