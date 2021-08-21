import { GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS, GET_COMPANY_FAIL, GET_COMPANY_REQUEST, GET_COMPANY_SUCCESS } from "../_constants/companyConstants";

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
            return {loading:false, company:action.payload}
        case GET_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}