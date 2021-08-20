import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getuser } from '../_actions/userActions'
import userIcon from '../images/user.svg'

function ProfileScreen(props) {
    const id = props.match.params.id

    const profileuser = useSelector(state => state.profileuser)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getuser(id))
    }, [dispatch])

    return (
        <div className="profile">
            <div className="profile-image">
                <img width="40px" src={profileuser.imageUrl? profileuser.imageUrl : userIcon} alt="imageUrl" />
            </div>

            <div className="profile-info-box">
                <div className="profile-info">
                    {profileuser.name}
                </div>

                <div className="profile-info">
                    {profileuser.email}
                </div>
                <div className="profile-info">
                    {profileuser.description}
                </div>
                <div className="profile-info">
                    {profileuser.phoneNumber}
                </div>
            </div>

            <div className="profile-teams">
                {
                    profileuser.teams&& profileuser.teams.map(t=>
                        <div>
                            {t.name}
                            {t.company}
                        </div>
                    )
                }
            </div>

        </div>
    )
}

export default ProfileScreen
