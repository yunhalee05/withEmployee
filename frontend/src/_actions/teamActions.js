import axios from "axios"
import { CREATE_TEAM_FAIL, CREATE_TEAM_REQUEST, CREATE_TEAM_SUCCESS, DELETE_TEAM_FAIL, DELETE_TEAM_REQUEST, DELETE_TEAM_SUCCESS, EDIT_TEAM_FAIL, EDIT_TEAM_REQUEST, EDIT_TEAM_SUCCESS, GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS, GET_TEAMS_FAIL, GET_TEAMS_REQUEST, GET_TEAMS_SUCCESS, GET_TEAM_FAIL, GET_TEAM_REQUEST, GET_TEAM_SUCCESS } from "../_constants/teamConstants"
import { ADD_USER_TEAM_FAIL, ADD_USER_TEAM_REQUEST, ADD_USER_TEAM_SUCCESS, DELETE_USER_TEAM_FAIL, DELETE_USER_TEAM_REQUEST, DELETE_USER_TEAM_SUCCESS } from "../_constants/userConstants"

export const getteamlist =(page) => async(dispatch, getState)=>{
    const {auth :{token}} = getState()

    dispatch({
        type:GET_TEAMLIST_REQUEST
    })

    try{
        const res = await axios.get(`/teams?page=${page}`,{
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
        const res = await axios.get(`/teams/${id}`, {
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
        const res = await axios.get(`/users/${id}/teams`, {
            headers : {Authorization : `Bearer ${token}`}
        })
        
        dispatch({
            type:GET_TEAMS_SUCCESS,
            payload:res.data.teams
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

export const createteam =(teamRequest) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()

    dispatch({
        type:CREATE_TEAM_REQUEST
    })

    try{
        const res = await axios.post('/teams',teamRequest, {
            headers : {Authorization : `Bearer ${token}`}
        })
        
        const data = {id:res.data.id, name:res.data.name, totalNumber:res.data.users.length}

        dispatch({
            type:CREATE_TEAM_SUCCESS,
            payload:data
        })

        return res.data.companyId
    }catch(error){
        dispatch({
            type:CREATE_TEAM_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const updateTeam =(id, teamRequest) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()

    dispatch({
        type:EDIT_TEAM_REQUEST
    })

    try{
        const res = await axios.post(`/teams/${id}`,teamRequest, {
            headers : {Authorization : `Bearer ${token}`}
        })

        const data = {id:res.data.id, name:res.data.name, totalNumber:res.data.users.length}
        
        dispatch({
            type:EDIT_TEAM_SUCCESS,
            payload:data
        })

        return res.data.companyId
    }catch(error){
        dispatch({
            type:EDIT_TEAM_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}


export const deleteteam =({teamId}) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()

    dispatch({
        type:DELETE_TEAM_REQUEST
    })

    try{
        await axios.delete(`/teams/${teamId}`, {
            headers : {Authorization : `Bearer ${token}`}
        })
        
        dispatch({
            type:DELETE_TEAM_SUCCESS,
            payload:teamId
        })

    }catch(error){
        dispatch({
            type:DELETE_TEAM_FAIL,
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
        const res = await axios.post(`/teams/${id}?email=${email}`,null,{
            headers : {Authorization : `Bearer ${token}`}
        })

        dispatch({
            type:ADD_USER_TEAM_SUCCESS,
            payload:res.data
        })

        return res.data
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

export const deleteuserteam =({teamId, userId}) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:DELETE_USER_TEAM_REQUEST
    })

    try{
        await axios.post(`/teams/${teamId}?userId=${userId}`,null,{
            headers : {Authorization : `Bearer ${token}`}
        })

        dispatch({
            type:DELETE_USER_TEAM_SUCCESS,
            payload:userId
        })

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

