import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import { adduserteam } from '../_actions/teamActions'

function AddMemberModal({members,setAddMember, id, setCeos, setLeaders, setMembers, ceos, leaders, member}) {

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
                if(res.role==="CEO"){
                    setCeos([...ceos,res])
                }else if(res.role==="LEADER"){
                    setLeaders([...leaders,res])
                }else if(res.role==="MEMBER"){
                    setMembers([...member,res])
                }
                setAddMember(false)
            })

    }
    return (
        <div className="add-modal form">
                <form onSubmit={handleSubmit}>
                    <div className="form-name">
                        Add Member
                    </div>

                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input type="email" className="form-control" id="email" name="email" onChange={e=>setEmail(e.target.value)} value={email} />
                    </div>

                    <div className="form-button">
                        <button type="submit" style={{marginRight:'10px'}} >Save</button>
                        <button onClick={()=> setAddMember(false)}>Cancel</button>
                    </div>
                </form>
        </div>
    )
}

export default AddMemberModal
