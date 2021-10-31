import { LOGIN_FAIL, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAIL, REGISTER_REQUEST, REGISTER_SUCCESS } from "../_constants/authConstants";

export const authReducer = (state={}, action)=>{
    switch(action.type){
        case REGISTER_REQUEST:
            return {...state, loading:true, error: ""}
        case REGISTER_SUCCESS:
            return {loading:false, user:{...action.payload.user}, token:action.payload.token, error: ""}
        case REGISTER_FAIL:
            return {...state, loading:false, error:action.payload}
        
        case LOGIN_REQUEST:
            return {...state, loading:true, error: ""}
        case LOGIN_SUCCESS:
            return {loading:false, user:{...action.payload.user}, token:action.payload.token, error: ""}
        case LOGIN_FAIL:
            return {...state, loading:false, error:action.payload}

        case LOGOUT:
            return {}

        default:
            return state;
    }
}