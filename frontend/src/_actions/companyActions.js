import axios from "axios"
import { CREATE_COMPANY_FAIL, CREATE_COMPANY_REQUEST, CREATE_COMPANY_SUCCESS, GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS, GET_COMPANY_FAIL, GET_COMPANY_REQUEST, GET_COMPANY_SUCCESS } from "../_constants/companyConstants"

export const getcompanylist =() => async(dispatch, getState)=>{

    const {auth: {token}} = getState()

    dispatch({
        type:GET_COMPANYLIST_REQUEST
    })

    try{
        const res = await axios.get('/company/companylist',{
            headers : {Authorization : `Bearer ${token}`}
        })
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

export const getcompany =({id}) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:GET_COMPANY_REQUEST
    })

    try{
        const res = await axios.get(`/company/${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        console.log(res)
        dispatch({
            type:GET_COMPANY_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_COMPANY_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const createCompany =(companyDTO) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:CREATE_COMPANY_REQUEST
    })

    try{
        const res = await axios.post('/company/save',companyDTO,{
            headers : {Authorization : `Bearer ${token}`}
        })
        console.log(res)
        dispatch({
            type:CREATE_COMPANY_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:CREATE_COMPANY_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}