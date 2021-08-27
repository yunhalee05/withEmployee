import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import { adduserteam } from '../_actions/userActions'

function AddMemberModal({members,setAddMember, id, setCeos, setLeaders, setMembers}) {

    const [email, setEmail] = useState('')

    const dispatch = useDispatch()

    const handleSubmit = (e)=>{
        e.preventDefault()

        if(members.length!==0){
            const existinguser = members.filter(user=>user.email ===email)
            if(existinguser.length >0){
                return window.alert('This member is alreay in the team.')
            }
        }

            dispatch(adduserteam({email, id})).then(res=>{
                dispatch(getteam({id})).then(res=>{
                    setCeos(res.users.filter(user=>user.role==="CEO"))
                    setLeaders(res.users.filter(user=>user.role==="Leader"))
                    setMembers(res.users.filter(user=>user.role==="Member"))
                })
                setAddMember(false)
            })

    }
    return (
        <div className="form">
                <form onSubmit={handleSubmit}>
                    <div className="form-name">
                        Add Member
                    </div>

                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input type="email" className="form-control" id="email" name="email" onChange={e=>setEmail(e.target.value)} value={email} />
                    </div>

                    {/* <div className="form-group">
                        <label htmlFor="role">Role</label>
                        <select defaultValue={role} onChange={(e)=>setRole(e.target.value)}>
                            <option value="Member" >Member</option>
                            <option value="Leader" >Leader</option>
                        </select>
                    </div> */}

                    <div className="form-button">
                        <button type="submit" >Save</button>
                        <button onClick={()=> setAddMember(false)}>Cancel</button>
                    </div>
                </form>
        </div>
    )
}

export default AddMemberModal
