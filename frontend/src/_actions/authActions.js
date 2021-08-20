import axios from "axios"
import { LOGIN_FAIL, LOGIN_REQUEST, LOGIN_SUCCESS, REGISTER_FAIL, REGISTER_REQUEST, REGISTER_SUCCESS } from "../_constants/authConstants"

export const login =({email, password}) => async(dispatch, getState)=>{
    dispatch({
        type:LOGIN_REQUEST
    })

    try{
        const res = await axios.post('/user/login', )
        dispatch({
            type:LOGIN_SUCCESS,
            payload:res.data
        })
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

export const register =({name, email, password, description, imageURL, phoneNumber}) => async(dispatch, getState)=>{
    dispatch({
        type:REGISTER_REQUEST
    })

    // console.log(name, email, password)

    const userDTO = {
        name: name, 
        email : email, 
        password :password,
        description: description,
        imageURL : imageURL,
        phoneNumber : phoneNumber
    }

    try{
        const res = await axios.post('/user/save',userDTO )
        dispatch({
            type:REGISTER_SUCCESS,
            payload:res.data
        })
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
