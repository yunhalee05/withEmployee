import { ADD_NEWCONVERSATION, CREATE_CONVERSATION_FAIL, CREATE_CONVERSATION_REQUEST, CREATE_CONVERSATION_SUCCESS, DELETE_CONVERSATION_FAIL, DELETE_CONVERSATION_REQUEST, DELETE_CONVERSATION_SUCCESS, GET_CONVERSATIONS_FAIL, GET_CONVERSATIONS_REQUEST, GET_CONVERSATIONS_SUCCESS, UPDATE_CONVERSATION_SUCCESS } from "../_constants/conversationConstants";
import { CREATE_MESSAGE_FAIL, CREATE_MESSAGE_REQUEST, CREATE_MESSAGE_SUCCESS, DELETE_MESSAGE_FAIL, DELETE_MESSAGE_REQUEST, DELETE_MESSAGE_SUCCESS, GET_MESSAGES_FAIL, GET_MESSAGES_REQUEST, GET_MESSAGES_SUCCESS } from "../_constants/messageConstants";

export const messageReducer = (state={}, action)=>{
    switch(action.type){
        case GET_CONVERSATIONS_REQUEST:
            return {...state, loading:true}
        case GET_CONVERSATIONS_SUCCESS:
            return {...state, loading:false, conversations:action.payload}
        case GET_CONVERSATIONS_FAIL:
            return {...state, loading:false, error:action.payload}

        case ADD_NEWCONVERSATION:
            return {...state, conversations:[...state.conversations, action.payload]}

        case CREATE_CONVERSATION_REQUEST:
            return {...state, loading:true}
        case CREATE_CONVERSATION_SUCCESS:
            return {...state, loading:false, conversations:[...state.conversations.filter(conversation=>conversation.id!=="new"),action.payload]}
        case CREATE_CONVERSATION_FAIL:
            return {...state, loading:false, error:action.payload}

        case DELETE_CONVERSATION_REQUEST:
            return {...state, loading:true}
        case DELETE_CONVERSATION_SUCCESS:
            return {...state, loading:false, 
                conversations:state.conversations.filter(conversation=>conversation.id !==action.payload),
                messages:state.messages.filter(message=> message.conversationId!==action.payload)}
        case DELETE_CONVERSATION_FAIL:
            return {...state, loading:false, error:action.payload}

        case GET_MESSAGES_REQUEST:
            return {...state, loading:true}
        case GET_MESSAGES_SUCCESS:
            return {...state, loading:false, messages:action.payload}
        case GET_MESSAGES_FAIL:
            return {...state, loading:false, error:action.payload}

        case CREATE_MESSAGE_REQUEST:
            return {...state, loading:true}
        case CREATE_MESSAGE_SUCCESS:
            return {...state, loading:false, messages:(state.messages && (state.messages[0].conversationId===action.payload.conversationId))?[...state.messages, action.payload]: state.messages, conversations:state.conversations.map(conversation=>conversation.id ===action.payload.conversationId ? {...conversation, text:action.payload.content, imageUrl:action.payload.imageUrl}:conversation)}
        case CREATE_MESSAGE_FAIL:
            return {...state, loading:false, error:action.payload}

        case DELETE_MESSAGE_REQUEST:
            return {...state, loading:true}
        case DELETE_MESSAGE_SUCCESS:
            return {...state, loading:false, messages: state.messages.filter(message=> message.id !== action.payload)}
        case DELETE_MESSAGE_FAIL:
            return {...state, loading:false, error:action.payload}

        default:
            return state;
    }
}