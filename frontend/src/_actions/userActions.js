import axios from "axios"
import { GET_USERLIST_FAIL, GET_USERLIST_REQUEST, GET_USERLIST_SUCCESS } from "../_constants/userConstants"

export const getuserlist =() => async(dispatch, getState)=>{
    dispatch({
        type:GET_USERLIST_REQUEST
    })

    try{
        const res = await axios.get('/user/userlist')
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