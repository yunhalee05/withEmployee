import axios from 'axios'
import React, { useEffect } from 'react'

function HomeScreen() {

    useEffect(() => {
        axios.get('/user/test')

    }, [])
    return (
        <div>
            
        </div>
    )
}

export default HomeScreen
