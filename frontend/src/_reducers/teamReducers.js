import { GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS, GET_TEAMS_FAIL, GET_TEAMS_REQUEST, GET_TEAMS_SUCCESS, GET_TEAM_FAIL, GET_TEAM_REQUEST, GET_TEAM_SUCCESS } from "../_constants/teamConstants";
import { ADD_USER_TEAM_FAIL, ADD_USER_TEAM_REQUEST, ADD_USER_TEAM_SUCCESS, DELETE_USER_TEAM_FAIL, DELETE_USER_TEAM_REQUEST, DELETE_USER_TEAM_SUCCESS } from "../_constants/userConstants";

export const teamlistReducer = (state={teams:[]}, action)=>{
    switch(action.type){
        case GET_TEAMLIST_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_TEAMLIST_SUCCESS:
            return {loading:false, teams:action.payload.teams, totalElement:action.payload.totalElement, totalPage:action.payload.totalPage, error: ""}
        case GET_TEAMLIST_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}

export const teamReducer = (state={teams:[]}, action)=>{
    switch(action.type){
        case GET_TEAM_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_TEAM_SUCCESS:
            return {...state, loading:false, team:action.payload, error: ""}
        case GET_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}

        case GET_TEAMS_REQUEST:
            return {...state, loading:true, error: ""}
        case GET_TEAMS_SUCCESS:
            return {...state, loading:false, teams:action.payload, error: ""}
        case GET_TEAMS_FAIL:
            return {...state, loading:false, error:action.payload}

        case ADD_USER_TEAM_REQUEST:
            return {...state, loading:true, error: ""}
        case ADD_USER_TEAM_SUCCESS:
            return {...state, loading:false, team:{...state.team, users:[...state.team.users,action.payload]}, error: ""};
        case ADD_USER_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}

        case DELETE_USER_TEAM_REQUEST:
            return {...state, loading:true, error: ""}
        case DELETE_USER_TEAM_SUCCESS:
            return {...state, loading:false, team:{...state.team, users:state.team.users.map(user=>user.id!==action.payload)}, error: ""};
        case DELETE_USER_TEAM_FAIL:
            return {...state, loading:false, error:action.payload}
        
        default:
            return state;
    }
}



