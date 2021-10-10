import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { CREATE_CONVERSATION_SUCCESS, DELETE_CONVERSATION_SUCCESS } from './_constants/conversationConstants'
import { CREATE_MESSAGE_SUCCESS, DELETE_MESSAGE_SUCCESS } from './_constants/messageConstants'

function SocketClient() {

    const auth = useSelector(state => state.auth)
    const socket = useSelector(state => state.socket)
    const {client} = socket

    const message = useSelector(state => state.message)
    const {messages} = message

    const dispatch = useDispatch()


    // Connect
    useEffect(() => {
        client.connect({}, () =>{
            console.log('Connected : ' + auth.user.id)
            client.send("/app/join", {},JSON.stringify(auth.user.id))

            // Create Message
            client.subscribe('/queue/addChatToClient/'+auth.user.id, function(messageDTO){
                const messagedto = JSON.parse(messageDTO.body)
                dispatch({
                    type:CREATE_MESSAGE_SUCCESS,
                    payload:messagedto
                })
            })
            // Delete Message
            client.subscribe('/queue/deleteChatToClient/'+auth.user.id,function(messageId){
                const id = JSON.parse(messageId.body)
                dispatch({
                    type:DELETE_MESSAGE_SUCCESS,
                    payload:id
                })
            })

            // Create Conversation
            client.subscribe('/queue/addConversationToClient/'+auth.user.id, function(conversationListDTO){
                const conversationDTO = JSON.parse(conversationListDTO.body)
                // console.log(conversationDTO)
                const newconversation = {
                    id:conversationDTO.id,
                    text:conversationDTO.text,
                    imageUrl : conversationDTO.imageUrl,
                    users: conversationDTO.users.filter(u=>u.id!==auth.user.id),
                    isTeamMember:conversationDTO.teamMember,
                    isSameCompany:conversationDTO.sameCompany,
                    isOtherCompany:conversationDTO.otherCompany
                }
                dispatch({
                    type:CREATE_CONVERSATION_SUCCESS,
                    payload:newconversation
                })
            })

            // Delete Conversation
            client.subscribe('/queue/deleteConversationToClient/'+auth.user.id, function(conversationId){
                dispatch({
                    type:DELETE_CONVERSATION_SUCCESS,
                    payload:JSON.parse(conversationId.body)
                })
            })


        })  

        return () => client.disconnect();

    }, [client, auth.user.id, dispatch])


    return (
        <div>
        </div>
    )
}

export default SocketClient
