import { LOGOUT } from "../_constants/authConstants";
import { EDIT_USER_FAIL, EDIT_USER_REQUEST, EDIT_USER_SUCCESS, GET_USERLIST_FAIL, GET_USERLIST_REQUEST, GET_USERLIST_SUCCESS, GET_USER_FAIL, GET_USER_REQUEST, GET_USER_SUCCESS } from "../_constants/userConstants";

export const userlistReducer = (state={users:[]}, action)=>{
    switch(action.type){
        case GET_USERLIST_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_USERLIST_SUCCESS:
            return {loading:false, users:action.payload.users, totalPage:action.payload.totalPage, totalElement:action.payload.totalElement, error: ""}
        case GET_USERLIST_FAIL:
            return {...state, loading:false, error:action.payload}

        case LOGOUT:
            return {} 

        default:
            return state;
    }
}

export const userReducer = (state={}, action)=>{
    switch(action.type){
        case GET_USER_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_USER_SUCCESS:
            return {loading:false, ...action.payload, error: ""}
        case GET_USER_FAIL:
            return {...state, loading:false, error:action.payload}

        case EDIT_USER_REQUEST:
            return {...state, loading:true, error: ""}
        case EDIT_USER_SUCCESS:
            return {...state, loading:false, name:action.payload.user.name, email:action.payload.user.email, description: action.payload.user.description, phoneNumber:action.payload.user.phoneNumber, error: ""}
        case EDIT_USER_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case LOGOUT:
            return {}

        default:
            return state;
    }
}


