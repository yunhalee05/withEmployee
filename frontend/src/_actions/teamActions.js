import axios from "axios"
import { CREATE_TEAM_FAIL, CREATE_TEAM_REQUEST, CREATE_TEAM_SUCCESS, DELETE_TEAM_FAIL, DELETE_TEAM_REQUEST, DELETE_TEAM_SUCCESS, EDIT_TEAM_FAIL, EDIT_TEAM_REQUEST, EDIT_TEAM_SUCCESS, GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS, GET_TEAMS_FAIL, GET_TEAMS_REQUEST, GET_TEAMS_SUCCESS, GET_TEAM_FAIL, GET_TEAM_REQUEST, GET_TEAM_SUCCESS } from "../_constants/teamConstants"

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
        const res = await axios.get(`/teams/${id}/users`, {
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

export const createteam =(teamDTO) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()

    dispatch({
        type:CREATE_TEAM_REQUEST
    })

    try{
        const res = await axios.post('/teams',teamDTO, {
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

export const editTeam =(teamDTO) => async(dispatch, getState)=>{

    const {auth :{token}} = getState()

    dispatch({
        type:EDIT_TEAM_REQUEST
    })

    try{
        const res = await axios.post('/teams',teamDTO, {
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

