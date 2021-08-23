import { ADD_USER_TEAM_FAIL, ADD_USER_TEAM_REQUEST, ADD_USER_TEAM_SUCCESS, DELETE_USER_TEAM_FAIL, DELETE_USER_TEAM_REQUEST, DELETE_USER_TEAM_SUCCESS, EDIT_USER_FAIL, EDIT_USER_REQUEST, EDIT_USER_SUCCESS, GET_USERLIST_FAIL, GET_USERLIST_REQUEST, GET_USERLIST_SUCCESS, GET_USER_FAIL, GET_USER_REQUEST, GET_USER_SUCCESS } from "../_constants/userConstants";

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
            return {loading:false, ...action.payload}
        case GET_USER_FAIL:
            return {...state, loading:false, error:action.payload}

        case EDIT_USER_REQUEST:
            return {...state, loading:true}
        case EDIT_USER_SUCCESS:
            return {loading:false, ...action.payload}
        case EDIT_USER_FAIL:
            return {...state, loading:false, error:action.payload}
            
        default:
            return state;
    }
}


export const adduserteamReducer = (state={}, action)=>{
    switch(action.type){
        case ADD_USER_TEAM_REQUEST:
            return {...state, loading:true}
        case ADD_USER_TEAM_SUCCESS:
            return {loading:false, ...action.payload}
        case ADD_USER_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}

export const deleteuserteamReducer = (state={}, action)=>{
    switch(action.type){
        case DELETE_USER_TEAM_REQUEST:
            return {...state, loading:true}
        case DELETE_USER_TEAM_SUCCESS:
            return {loading:false, deletedId : action.payload}
        case DELETE_USER_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}
