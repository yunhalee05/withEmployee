import axios from "axios"
import { CREATE_CONVERSATION_FAIL, CREATE_CONVERSATION_REQUEST, CREATE_CONVERSATION_SUCCESS, DELETE_CONVERSATION_FAIL, DELETE_CONVERSATION_REQUEST, DELETE_CONVERSATION_SUCCESS, GET_CONVERSATIONS_FAIL, GET_CONVERSATIONS_REQUEST, GET_CONVERSATIONS_SUCCESS } from "../_constants/conversationConstants"

export const getConversations =() => async(dispatch, getState)=>{

    const {auth : {token}} = getState()
    const {auth : {user}} = getState()

    dispatch({
        type:GET_CONVERSATIONS_REQUEST
    })

    try{
        const res = await axios.get(`/conversations?userId=${user.id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })

        console.log(res)
        const newArr = [];

        res.data.conversations.forEach(item=> {
            newArr.push({
                        id:item.id, 
                        text:item.text, 
                        imageUrl:item.imageUrl, 
                        users: item.users.filter(u=> u.id!==user.id),
                        isTeamMember:item.teamMember,
                        isSameCompany:item.sameCompany,
                        isOtherCompany:item.otherCompany
                    })
        })

        dispatch({
            type:GET_CONVERSATIONS_SUCCESS,
            payload:newArr
        })

        return newArr

    }catch(error){

        dispatch({
            type:GET_CONVERSATIONS_FAIL,
            paylaod:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }

}

export const createConversation = (conversationRequest) =>async(dispatch, getState)=>{
    const {auth : {token}} = getState()
    const {auth : {user}} = getState() 
    const {socket : {client}} = getState()

    dispatch({
        type:CREATE_CONVERSATION_REQUEST
    })

    try{
        const res = await axios.post(`/conversations`,conversationRequest,{
            headers : {Authorization : `Bearer ${token}`}
        })

        const newconversation = {
            id:res.data.id,
            text:res.data.text,
            imageUrl : res.data.imageUrl,
            users: res.data.users.filter(u=>u.id!==user.id),
            isTeamMember:res.data.teamMember,
            isSameCompany:res.data.sameCompany,
            isOtherCompany:res.data.otherCompany
        }

        dispatch({
            type:CREATE_CONVERSATION_SUCCESS,
            payload:newconversation
        })

        newconversation.users.forEach(user=>{
            client.send(`/app/chat/addConversation/${user.id}`,{},JSON.stringify(res.data))
        })

        return newconversation

    }catch(error){
        dispatch({
            type:CREATE_CONVERSATION_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const deleteConversation = (conversation) =>async(dispatch, getState)=>{
    const {auth : {token}} = getState()
    const {socket : {client}} = getState()

    dispatch({
        type:DELETE_CONVERSATION_REQUEST
    })

    try{
        await axios.delete(`/conversations/${conversation.id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })

        dispatch({
            type:DELETE_CONVERSATION_SUCCESS,
            payload:conversation.id
        })

        conversation.users.forEach(user=>{
            client.send(`/app/chat/deleteConversation/${user.id}`,{},JSON.stringify(conversation.id))
        })
        
        // return newconversation
    }catch(error){
        dispatch({
            type:DELETE_CONVERSATION_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}