import axios from "axios"
import { CREATE_COMPANY_FAIL, CREATE_COMPANY_REQUEST, CREATE_COMPANY_SUCCESS, DELETE_COMPANY_REQUEST, DELETE_COMPANY_SUCCESS, EDIT_COMPANY_FAIL, EDIT_COMPANY_REQUEST, EDIT_COMPANY_SUCCESS, GET_ALL_COMPANIES_FAIL, GET_ALL_COMPANIES_REQUEST, GET_ALL_COMPANIES_SUCCESS, GET_COMPANIES_FAIL, GET_COMPANIES_RECOMMENDATION_FAIL, GET_COMPANIES_RECOMMENDATION_REQUEST, GET_COMPANIES_RECOMMENDATION_SUCCESS, GET_COMPANIES_REQUEST, GET_COMPANIES_SEARCH_FAIL, GET_COMPANIES_SEARCH_REQUEST, GET_COMPANIES_SEARCH_SUCCESS, GET_COMPANIES_SUCCESS, GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS, GET_COMPANY_FAIL, GET_COMPANY_REQUEST, GET_COMPANY_SUCCESS } from "../_constants/companyConstants"

export const getcompanylist =(page) => async(dispatch, getState)=>{

    const {auth: {token}} = getState()

    dispatch({
        type:GET_COMPANYLIST_REQUEST
    })

    try{
        const res = await axios.get(`/companies?page=${page}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
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

export const getallcompaniesbypage =(page, sort) => async(dispatch, getState)=>{

    const {auth: {token}} = getState()

    dispatch({
        type:GET_ALL_COMPANIES_REQUEST
    })

    try{
        const res = await axios.get(`/companies?page=${page}&sort=${sort}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        dispatch({
            type:GET_ALL_COMPANIES_SUCCESS,
            payload:res.data
        })
    }catch(error){
        dispatch({
            type:GET_ALL_COMPANIES_FAIL,
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
        const res = await axios.get(`/companies/${id}`,{
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
        const res = await axios.get(`/users/${id}/companies`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_COMPANIES_SUCCESS,
            payload:res.data.companies
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

export const createCompany =(companyRequest) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:CREATE_COMPANY_REQUEST
    })

    try{
        const res = await axios.post('/companies',companyRequest,{
            headers : {Authorization : `Bearer ${token}`}
        })
        
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

export const editCompany =(id, companyRequest) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:EDIT_COMPANY_REQUEST
    })

    try{
        const res = await axios.post(`/companies/${id}`,companyRequest,{
            headers : {Authorization : `Bearer ${token}`}
        })

        dispatch({
            type:EDIT_COMPANY_SUCCESS,
            payload:res.data
        })
        return res.data

    }catch(error){
        dispatch({
            type:EDIT_COMPANY_FAIL,
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
        await axios.delete(`/companies/${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        dispatch({
            type:DELETE_COMPANY_SUCCESS,
            payload:id
        })
        return id

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
        const res = await axios.get('/companies/recommendation',{
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_COMPANIES_RECOMMENDATION_SUCCESS,
            payload:res.data.companies
        })

        return res.data.companies
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

export const getcompaniessearch =(keyword) => async(dispatch, getState)=>{

    const {auth : {token}} = getState()

    dispatch({
        type:GET_COMPANIES_SEARCH_REQUEST
    })

    try{
        const res = await axios.get(`/companies?keyword=${keyword}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        // console.log(res)
        dispatch({
            type:GET_COMPANIES_SEARCH_SUCCESS,
            payload:res.data.companies
        })

        return res.data.companies
    }catch(error){
        dispatch({
            type:GET_COMPANIES_SEARCH_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}