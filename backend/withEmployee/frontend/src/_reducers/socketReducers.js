import { LOGOUT } from "../_constants/authConstants";
import { SOCKET } from "../_constants/socketConstants";

export const socketReducer = (state={}, action)=>{
    switch(action.type){
        case SOCKET:
            return action.payload

        case LOGOUT:
            return {}

        default:
            return state;
    }
}