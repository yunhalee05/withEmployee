import { CREATE_COMPANY_FAIL, CREATE_COMPANY_REQUEST, CREATE_COMPANY_SUCCESS, DELETE_COMPANY_FAIL, DELETE_COMPANY_REQUEST, DELETE_COMPANY_SUCCESS, GET_COMPANIES_REQUEST, GET_COMPANIES_SUCCESS, GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS, GET_COMPANY_FAIL, GET_COMPANY_REQUEST, GET_COMPANY_SUCCESS } from "../_constants/companyConstants";

export const companylistReducer = (state={companies:[]}, action)=>{
    switch(action.type){
        case GET_COMPANYLIST_REQUEST:
            return {...state, loading:true}
        case GET_COMPANYLIST_SUCCESS:
            return {loading:false, companies:action.payload}
        case GET_COMPANYLIST_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}

export const companyReducer = (state={}, action)=>{
    switch(action.type){
        case GET_COMPANY_REQUEST:
            return {...state, loading:true}
        case GET_COMPANY_SUCCESS:
            return {...state, loading:false, company:action.payload}
        case GET_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}

        case GET_COMPANIES_REQUEST:
            return {...state, loading:true}
        case GET_COMPANIES_SUCCESS:
            return {...state, loading:false, companies:action.payload}
        case GET_COMPANYLIST_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case CREATE_COMPANY_REQUEST:
            return {...state, loading:true}
        case CREATE_COMPANY_SUCCESS:
            return {...state, loading:false, companies:[...state.companies, action.payload]}
        case CREATE_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}

        case DELETE_COMPANY_REQUEST:
            return {...state, loading:true}
        case DELETE_COMPANY_SUCCESS:
            return {...state, loading:false, companies: state.companies.filter(company=>company.id!==action.payload)}
        case DELETE_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}

        
        default:
            return state;
    }
}