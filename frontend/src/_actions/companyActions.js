import axios from "axios"
import { GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS } from "../_constants/companyConstants"

export const getcompanylist =() => async(dispatch, getState)=>{
    dispatch({
        type:GET_COMPANYLIST_REQUEST
    })

    try{
        const res = await axios.get('/company/companylist')
        dispatch({
            type:GET_COMPANYLIST_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_COMPANYLIST_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}