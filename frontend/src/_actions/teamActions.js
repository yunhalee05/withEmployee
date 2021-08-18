import axios from "axios"
import { GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS } from "../_constants/teamConstants"

export const getteamlist =() => async(dispatch, getState)=>{
    dispatch({
        type:GET_TEAMLIST_REQUEST
    })

    try{
        const res = await axios.get('/team/teamlist')
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