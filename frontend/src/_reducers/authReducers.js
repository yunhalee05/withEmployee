import { REGISTER_FAIL, REGISTER_REQUEST, REGISTER_SUCCESS } from "../_constants/authConstants";

export const authReducer = (state={}, action)=>{
    switch(action.type){
        case REGISTER_REQUEST:
            return {...state, loading:true}
        case REGISTER_SUCCESS:
            return {loading:false, user:{...action.payload}}
        case REGISTER_FAIL:
            return {...state, loading:false, error:action.payload}
        default:
            return state;
    }
}