import { GET_USERLIST_FAIL, GET_USERLIST_REQUEST, GET_USERLIST_SUCCESS } from "../_constants/userConstants";

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