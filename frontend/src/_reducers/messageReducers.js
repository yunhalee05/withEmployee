import { CREATE_CONVERSATION_FAIL, CREATE_CONVERSATION_REQUEST, CREATE_CONVERSATION_SUCCESS, GET_CONVERSATIONS_FAIL, GET_CONVERSATIONS_REQUEST, GET_CONVERSATIONS_SUCCESS } from "../_constants/conversationConstants";

export const messageReducer = (state={}, action)=>{
    switch(action.type){
        case GET_CONVERSATIONS_REQUEST:
            return {...state, loading:true}
        case GET_CONVERSATIONS_SUCCESS:
            return {...state, loading:false, conversations:action.payload}
        case GET_CONVERSATIONS_FAIL:
            return {...state, loading:false, error:action.payload}

        case CREATE_CONVERSATION_REQUEST:
            return {...state, loading:true}
        case CREATE_CONVERSATION_SUCCESS:
            return {...state, loading:false, conversations:[...state.conversations,action.payload]}
        case CREATE_CONVERSATION_FAIL:
            return {...state, loading:false, error:action.payload}

        default:
            return state;
    }
}