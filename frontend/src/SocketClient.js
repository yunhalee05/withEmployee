import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
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

            
        })  
        return () => client.disconnect();

    }, [client, auth.user.id, dispatch])


    return (
        <div>
        </div>
    )
}

export default SocketClient
