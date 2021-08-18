import { GET_TEAMLIST_FAIL, GET_TEAMLIST_REQUEST, GET_TEAMLIST_SUCCESS } from "../_constants/teamConstants";

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