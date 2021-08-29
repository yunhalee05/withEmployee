import axios from "axios"
import { CREATE_CONVERSATION_FAIL, CREATE_CONVERSATION_REQUEST, CREATE_CONVERSATION_SUCCESS, GET_CONVERSATIONS_FAIL, GET_CONVERSATIONS_REQUEST, GET_CONVERSATIONS_SUCCESS } from "../_constants/conversationConstants"

export const getConversations =() => async(dispatch, getState)=>{

    const {auth : {token}} = getState()
    const {auth : {user}} = getState()

    dispatch({
        type:GET_CONVERSATIONS_REQUEST
    })

    try{
        const res = await axios.get(`/conversations?id=${user.id}`,{
            headers : {Authorization : `Bearer ${token}`}
        })
        const newArr = [];

        res.data.forEach(item=> {
            newArr.push({
                        id:item.id, 
                        text:item.text, 
                        imageUrl:item.imageUrl, 
                        users: item.users.filter(u=> u.id!==user.id)})
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

export const createConversation = (toEmail) =>async(dispatch, getState)=>{
    const {auth : {token}} = getState()
    const {auth : {user}} = getState() 

    dispatch({
        type:CREATE_CONVERSATION_REQUEST
    })

    try{
        const res = await axios.post(`/conversation?toEmail=${toEmail}&fromEmail=${user.email}`,null,{
            headers : {Authorization : `Bearer ${token}`}
        })

        const newconversation = {
            id:res.data.id,
            text:res.data.text,
            imageUrl : res.data.imageUrl,
            users: res.data.users.filter(u=>u.id!==user.id)
        }
        dispatch({
            type:CREATE_CONVERSATION_SUCCESS,
            payload:newconversation
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