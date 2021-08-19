import { GET_USERLIST_FAIL, GET_USERLIST_REQUEST, GET_USERLIST_SUCCESS, GET_USER_FAIL, GET_USER_REQUEST, GET_USER_SUCCESS } from "../_constants/userConstants";

export const userlistReducer = (state={users:[]}, action)=>{
    switch(action.type){
        case GET_USERLIST_REQUEST:
            return {...state, loading:true}
        case GET_USERLIST_SUCCESS:
            return {loading:false, users:action.payload}
        case GET_USERLIST_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}

export const userReducer = (state={}, action)=>{
    switch(action.type){
        case GET_USER_REQUEST:
            return {...state, loading:true}
        case GET_USER_SUCCESS:
            return {loading:false, user:action.payload}
        case GET_USER_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}