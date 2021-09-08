import axios from "axios"
import { CREATE_COMPANY_FAIL, CREATE_COMPANY_REQUEST, CREATE_COMPANY_SUCCESS, DELETE_COMPANY_REQUEST, DELETE_COMPANY_SUCCESS, GET_COMPANIES_FAIL, GET_COMPANIES_RECOMMENDATION_FAIL, GET_COMPANIES_RECOMMENDATION_REQUEST, GET_COMPANIES_RECOMMENDATION_SUCCESS, GET_COMPANIES_REQUEST, GET_COMPANIES_SUCCESS, GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS, GET_COMPANY_FAIL, GET_COMPANY_REQUEST, GET_COMPANY_SUCCESS } from "../_constants/companyConstants"

export const getcompanylist =(page) => async(dispatch, getState)=>{

    const {auth: {token}} = getState()

    dispatch({
        type:GET_COMPANYLIST_REQUEST
    })

    try{
        const res = await axios.get(`/company/companylist?page=${page}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_COMPANYLIST_SUCCESS,
            payload:res.data
        })
        return res.data
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
        // console.log(res)
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

export const getcompanies =({id}) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:GET_COMPANIES_REQUEST
    })

    try{
        const res = await axios.get(`/companies/${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_COMPANIES_SUCCESS,
            payload:res.data
        })

        return res.data
    }catch(error){
        dispatch({
            type:GET_COMPANIES_FAIL,
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
        return res.data

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

export const deleteCompany =(id) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:DELETE_COMPANY_REQUEST
    })

    try{
        const res = await axios.delete(`/company/delete/${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        console.log(res)
        dispatch({
            type:DELETE_COMPANY_SUCCESS,
            payload:res.data
        })
        return res.data

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



export const getcompaniesrecommendation =() => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:GET_COMPANIES_RECOMMENDATION_REQUEST
    })

    try{
        const res = await axios.get('/company/recommendation',{
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_COMPANIES_RECOMMENDATION_SUCCESS,
            payload:res.data
        })

        return res.data
    }catch(error){
        dispatch({
            type:GET_COMPANIES_RECOMMENDATION_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}