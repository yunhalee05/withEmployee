import { GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS, GET_TEAM_FAIL, GET_TEAM_REQUEST, GET_TEAM_SUCCESS } from "../_constants/teamConstants";

export const teamlistReducer = (state={teams:[]}, action)=>{
    switch(action.type){
        case GET_TEAMLIST_REQUEST:
            return {...state, loading:true}
        case GET_TEAMLIST_SUCCESS:
            return {loading:false, teams:action.payload}
        case GET_TEAMLIST_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}

export const teamReducer = (state={teams:[]}, action)=>{
    switch(action.type){
        case GET_TEAM_REQUEST:
            return {...state, loading:true}
        case GET_TEAM_SUCCESS:
            return {loading:false, teams:action.payload}
        case GET_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}