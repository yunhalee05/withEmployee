import axios from "axios"
import { CREATE_MESSAGE_FAIL, CREATE_MESSAGE_REQUEST, CREATE_MESSAGE_SUCCESS, DELETE_MESSAGE_FAIL, DELETE_MESSAGE_REQUEST, DELETE_MESSAGE_SUCCESS, GET_MESSAGES_FAIL, GET_MESSAGES_REQUEST, GET_MESSAGES_SUCCESS } from "../_constants/messageConstants"

export const getMessages = (id) =>async(dispatch, getState)=>{
    const {auth : {token}} = getState()

    dispatch({
        type:GET_MESSAGES_REQUEST
    })

    try{
        const res = await axios.get(`/messages/${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })

        const newArr = res.data.reverse()
    
        dispatch({
            type:GET_MESSAGES_SUCCESS,
            payload:newArr
        })

        return newArr
        
    }catch(error){
        dispatch({
            type:GET_MESSAGES_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const createMessage = (messageDTO, conversation) =>async(dispatch, getState)=>{
    const {auth : {token}} = getState()
    const {socket : {client}} = getState()

    dispatch({
        type:CREATE_MESSAGE_REQUEST
    })

    try{
        const res = await axios.post('/message',messageDTO,{
            headers : {Authorization : `Bearer ${token}`}
        })
    
        dispatch({
            type:CREATE_MESSAGE_SUCCESS,
            payload:res.data
        })

        conversation.users.forEach(user=>{
            client.send(`/app/chat/${user.id}`,{},JSON.stringify(res.data))
        })

        return res.data
        
    }catch(error){
        dispatch({
            type:CREATE_MESSAGE_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

export const deleteMessage = (id, conversation) =>async(dispatch, getState)=>{
    const {auth : {token}} = getState()
    const {socket : {client}} = getState()

    dispatch({
        type:DELETE_MESSAGE_REQUEST
    })

    try{
        const res = await axios.delete(`/message/delete?id=${id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
    
        dispatch({
            type:DELETE_MESSAGE_SUCCESS,
            payload:res.data
        })

        conversation.users.forEach(user=>{
            client.send(`/app/chat/delete/${user.id}`,{},JSON.stringify(res.data))
        })


        return res.data
        
    }catch(error){
        dispatch({
            type:DELETE_MESSAGE_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}