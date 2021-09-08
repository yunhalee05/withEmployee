import axios from "axios"
import { LOGIN_SUCCESS } from "../_constants/authConstants"
import { ADD_USER_TEAM_FAIL, ADD_USER_TEAM_REQUEST, ADD_USER_TEAM_SUCCESS, DELETE_USER_TEAM_FAIL, DELETE_USER_TEAM_REQUEST, DELETE_USER_TEAM_SUCCESS, EDIT_USER_FAIL, EDIT_USER_REQUEST, EDIT_USER_SUCCESS, GET_USERLIST_FAIL, GET_USERLIST_REQUEST, GET_USERLIST_SUCCESS, GET_USER_FAIL, GET_USER_REQUEST, GET_USER_SUCCESS } from "../_constants/userConstants"

export const getuserlist =(page) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()
    dispatch({
        type:GET_USERLIST_REQUEST
    })

    try{
        const res = await axios.get(`/user/userlist?page=${page}`, {
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_USERLIST_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_USERLIST_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const getuser =(id) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:GET_USER_REQUEST
    })

    try{
        const res = await axios.get(`/user/${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })

        // console.log(res)
        dispatch({
            type:GET_USER_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_USER_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const edituser =(bodyFormData) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:EDIT_USER_REQUEST
    })

    try{
        const res = await axios.post('/user/save',bodyFormData,{
            headers : {Authorization : `Bearer ${token}`}
        })

        console.log(res) 
        dispatch({
            type:EDIT_USER_SUCCESS,
            payload:res.data
        })

        dispatch({
            type:LOGIN_SUCCESS,
            payload:{
                user:res.data,
                token:token
            }
        })

        localStorage.setItem("auth", JSON.stringify({user:res.data, token:token}))

    }catch(error){
        dispatch({
            type:EDIT_USER_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}


export const adduserteam =({email, id}) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:ADD_USER_TEAM_REQUEST
    })

    try{
        const res = await axios.post(`/user/addTeam?email=${email}&id=${id}`,null,{
            headers : {Authorization : `Bearer ${token}`}
        })

        // console.log(res)
        dispatch({
            type:ADD_USER_TEAM_SUCCESS,
            payload:res.data
        })

        return res.data.id
    }catch(error){
        dispatch({
            type:ADD_USER_TEAM_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const deleteuserteam =({userId, teamId}) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:DELETE_USER_TEAM_REQUEST
    })

    try{
        const res = await axios.delete(`/user/deleteTeam?userId=${userId}&teamId=${teamId}`,null,{
            headers : {Authorization : `Bearer ${token}`}
        })

        // console.log(res)
        dispatch({
            type:DELETE_USER_TEAM_SUCCESS,
            payload:res.data
        })

        return res.data
    }catch(error){
        dispatch({
            type:DELETE_USER_TEAM_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}