import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getuser } from '../_actions/userActions'
import userIcon from '../images/user.svg'
import EditProfileModal from '../components/EditProfileModal'
import Loading from '../components/Loading'
import Error from '../components/Error'

function ProfileScreen(props) {
    const id = props.match.params.id

    const profileuser = useSelector(state => state.profileuser)
    const auth = useSelector(state => state.auth)
    
    const [onEdit, setOnEdit] = useState(false)   

    const dispatch = useDispatch()

    useEffect(() => {
        if(!profileuser.id || profileuser.id != id){
            dispatch(getuser(id))
        }
    }, [id, profileuser, profileuser.id])

    return (
        <div className="profile">
            {
                profileuser.error && <Error error={profileuser.error}/>
            }
            {
                profileuser.loading && <Loading/>
            }
            {   profileuser.loading ===false &&
            (
                <div className="user-info">
                    <div className="profile-image">
                        <img width="40px" src={profileuser.imageUrl? profileuser.imageUrl : userIcon} alt="imageUrl" />
                    </div>

                    <div className="profile-info-box">
                        <div className="profile-info">
                            <label htmlFor="">Name : </label>
                            {profileuser.name}
                        </div>

                        <div className="profile-info">
                            <label htmlFor="">Role : </label>
                            {profileuser.role}
                        </div>

                        <div className="profile-info">
                            <label htmlFor="">E-mail : </label>
                            {profileuser.email}
                        </div>

                        <div className="profile-info">
                            <label htmlFor="">Phone Number : </label>
                            {profileuser.phoneNumber}
                        </div>

                        <div className="profile-info">
                            <label htmlFor="">Description : </label>
                            {profileuser.description}
                        </div>
                    </div>

                    {
                        auth.user.id === profileuser.id &&
                        <div className="profile-edit-button">
                            <button onClick={()=>setOnEdit(!onEdit)}>Edit profile</button>
                        </div>
                    }
                </div>
            )
            }

            <div className="profile-userteam-title">WHERE I BELONG TO <i className="far fa-hand-point-down"></i></div>

            <div className="team-card-container">
                {
                    profileuser.teams&& profileuser.teams.map(t=>
                        <div className="team-card" key={t.id}>
                            <div className="team-name">{t.name}</div>
                            <div className="company-name">{t.company}</div>
                        </div>
                    )
                }
            </div>

            {
                onEdit &&
                <EditProfileModal user={profileuser} setOnEdit={setOnEdit}/>
            }

        </div>
    )
}

export default ProfileScreen
