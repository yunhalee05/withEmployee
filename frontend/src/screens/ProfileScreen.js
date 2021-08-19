import React, { useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { getuser } from '../_actions/userActions'

function ProfileScreen(props) {
    const id = props.match.params.id

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getuser(id))
    }, [dispatch])
    return (
        <div>
            profile
        </div>
    )
}

export default ProfileScreen
