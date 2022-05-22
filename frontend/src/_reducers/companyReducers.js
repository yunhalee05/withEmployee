import { LOGOUT } from "../_constants/authConstants";
import { CREATE_COMPANY_FAIL, CREATE_COMPANY_REQUEST, CREATE_COMPANY_SUCCESS, DELETE_COMPANY_FAIL, DELETE_COMPANY_REQUEST, DELETE_COMPANY_SUCCESS, EDIT_COMPANY_FAIL, EDIT_COMPANY_REQUEST, EDIT_COMPANY_SUCCESS, GET_ALL_COMPANIES_FAIL, GET_ALL_COMPANIES_REQUEST, GET_ALL_COMPANIES_SUCCESS, GET_COMPANIES_RECOMMENDATION_FAIL, GET_COMPANIES_RECOMMENDATION_REQUEST, GET_COMPANIES_RECOMMENDATION_SUCCESS, GET_COMPANIES_REQUEST, GET_COMPANIES_SEARCH_FAIL, GET_COMPANIES_SEARCH_REQUEST, GET_COMPANIES_SEARCH_SUCCESS, GET_COMPANIES_SUCCESS, GET_COMPANYLIST_FAIL, GET_COMPANYLIST_REQUEST, GET_COMPANYLIST_SUCCESS, GET_COMPANY_FAIL, GET_COMPANY_REQUEST, GET_COMPANY_SUCCESS, GET_LOAD_MORE_COMPANIES } from "../_constants/companyConstants";
import { CREATE_TEAM_FAIL, CREATE_TEAM_REQUEST, CREATE_TEAM_SUCCESS, DELETE_TEAM_FAIL, DELETE_TEAM_REQUEST, DELETE_TEAM_SUCCESS, EDIT_TEAM_FAIL, EDIT_TEAM_REQUEST, EDIT_TEAM_SUCCESS } from "../_constants/teamConstants";

export const companylistReducer = (state={companies:[]}, action)=>{
    switch(action.type){
        case GET_COMPANYLIST_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_COMPANYLIST_SUCCESS:
            return {...state, loading:false, companylist:action.payload.companies, totalElement:action.payload.totalElement, totalPage:action.payload.totalPage, error: ""}
        case GET_COMPANYLIST_FAIL:
            return {...state, loading:false, error:action.payload}

        case GET_ALL_COMPANIES_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_ALL_COMPANIES_SUCCESS:
            return {...state, loading:false, companies:action.payload.companies, totalElement:action.payload.totalElement, totalPage:action.payload.totalPage, error: ""}
        case GET_ALL_COMPANIES_FAIL:
            return {...state, loading:false, error:action.payload}
        case GET_LOAD_MORE_COMPANIES:
            return {...state, loading:false, companies:[...state.companies, ...action.payload], error: ""}

        case GET_COMPANIES_RECOMMENDATION_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_COMPANIES_RECOMMENDATION_SUCCESS:
            return {...state,loading:false, recommendation:action.payload , error: ""}
        case GET_COMPANIES_RECOMMENDATION_FAIL:
            return {...state, loading:false, error:action.payload}
            
        case GET_COMPANIES_SEARCH_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_COMPANIES_SEARCH_SUCCESS:
            return {...state,loading:false, search:action.payload , error: ""}
        case GET_COMPANIES_SEARCH_FAIL:
            return {...state, loading:false, error:action.payload}

        case LOGOUT:
            return {}
            
        default:
            return state;
    }
}

export const companyReducer = (state={}, action)=>{
    switch(action.type){
        case GET_COMPANY_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_COMPANY_SUCCESS:
            return {...state, loading:false, company:action.payload, error: ""}
        case GET_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}

        case GET_COMPANIES_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_COMPANIES_SUCCESS:
            return {...state, loading:false, companies:action.payload, error: ""}
        case GET_COMPANYLIST_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case CREATE_COMPANY_REQUEST:
            return {...state, loading:true, error: ""}
        case CREATE_COMPANY_SUCCESS:
            return {...state, loading:false, companies:[...state.companies, action.payload], error: ""}
        case CREATE_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case EDIT_COMPANY_REQUEST:
            return {...state, loading:true, error: ""}
        case EDIT_COMPANY_SUCCESS:
            return {...state, loading:false, companies:state.companies.map(company=>company.id===action.payload.id ? action.payload:company), error: ""}
        case EDIT_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}

        case DELETE_COMPANY_REQUEST:
            return {...state, loading:true, error: ""}
        case DELETE_COMPANY_SUCCESS:
            return {...state, loading:false, companies: state.companies.filter(company=>company.id!==action.payload), error: ""}
        case DELETE_COMPANY_FAIL:
            return {...state, loading:false, error:action.payload}

        
        case CREATE_TEAM_REQUEST:
            return {...state, loading:true, error: ""}
        case CREATE_TEAM_SUCCESS:
            if(state.company.teams) {
                return {...state, loading:false, company:{...state.company, teams:[...state.company.teams, action.payload]}, error: ""}
            }else {
                return {...state, loading:false, company:{...state.company}, error: ""}
            }
        case CREATE_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case EDIT_TEAM_REQUEST:
            return {...state, loading:true, error: ""}
        case EDIT_TEAM_SUCCESS:
            return {...state, loading:false, company:{...state.company, teams:state.company.teams.map(team=> team.id===action.payload.id? action.payload: team)}, error: ""}
        case EDIT_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case DELETE_TEAM_REQUEST:
            return {...state, loading:true, error: ""}
        case DELETE_TEAM_SUCCESS:
            return {...state, loading:false, company:{...state.company, teams:state.company.teams.filter(team=> team.id!==action.payload)}, error: ""}
        case DELETE_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}

        case LOGOUT:
            return {}

        default:
            return state;
    }
}