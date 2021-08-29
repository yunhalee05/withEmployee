import React, { useEffect } from 'react'
import { useSelector } from 'react-redux'

function SocketClient() {

    const auth = useSelector(state => state.auth)
    const socket = useSelector(state => state.socket)
    const {client} = socket

    // Connect
    useEffect(() => {
        client.connect({}, () =>{
            console.log('Connected : ' + auth.user.id)
            client.send("/app/join", {},JSON.stringify(auth.user.id))
        })  
    }, [client, auth.user.id])

    

    return (
        <div>
        </div>
    )
}

export default SocketClient
