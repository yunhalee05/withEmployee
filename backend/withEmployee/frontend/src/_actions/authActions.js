import axios from "axios"
import { LOGIN_FAIL, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAIL, REGISTER_REQUEST, REGISTER_SUCCESS } from "../_constants/authConstants"

export const login =({email, password}) => async(dispatch, getState)=>{
    dispatch({
        type:LOGIN_REQUEST
    })

    const body= {
        username:email,
        password:password
    }

    try{
        const res = await axios.post('/login', body)

        dispatch({
            type:LOGIN_SUCCESS,
            payload:res.data
        })

        localStorage.setItem("token", JSON.stringify(res.data.token).replace(/\"/gi, ""))
    }catch(error){
        dispatch({
            type:LOGIN_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const register =(bodyFormData, email, password) => async(dispatch, getState)=>{
    dispatch({
        type:REGISTER_REQUEST
    })

    try{
        let res;
        await axios.post('/users', bodyFormData).then(async(r)=>{
            res = await axios.post('/login',{username:email,password:password })
        })

        dispatch({
            type:REGISTER_SUCCESS,
            payload:res.data
        })

        localStorage.setItem("token", JSON.stringify(res.data.token).replace(/\"/gi, ""))
        return res.data.user.id
    
    }catch(error){
        dispatch({
            type:REGISTER_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}


export const logout = () =>(dispatch)=>{
    dispatch({
        type:LOGOUT
    })

    localStorage.removeItem("token")
}
