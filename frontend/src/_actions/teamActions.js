import axios from "axios"
import { GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS, GET_TEAMS_FAIL, GET_TEAMS_REQUEST, GET_TEAMS_SUCCESS, GET_TEAM_FAIL, GET_TEAM_REQUEST, GET_TEAM_SUCCESS } from "../_constants/teamConstants"

export const getteamlist =() => async(dispatch, getState)=>{
    const {auth :{token}} = getState()

    dispatch({
        type:GET_TEAMLIST_REQUEST
    })

    try{
        const res = await axios.get('/team/teamlist',{
            headers : {Authorization : `Bearer ${token}`}
        })
        dispatch({
            type:GET_TEAMLIST_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_TEAMLIST_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const getteam =({id}) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()
    dispatch({
        type:GET_TEAM_REQUEST
    })

    try{
        const res = await axios.get(`/team/${id}`, {
            headers : {Authorization : `Bearer ${token}`}
        })
        
        dispatch({
            type:GET_TEAM_SUCCESS,
            payload:res.data
        })

        return res.data
    }catch(error){
        dispatch({
            type:GET_TEAM_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const getteams =({id}) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()

    dispatch({
        type:GET_TEAMS_REQUEST
    })

    try{
        const res = await axios.get(`/teams/${id}`, {
            headers : {Authorization : `Bearer ${token}`}
        })
        
        dispatch({
            type:GET_TEAMS_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_TEAMS_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

