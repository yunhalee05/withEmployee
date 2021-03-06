import axios from 'axios'
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { createteam, updateTeam } from '../_actions/teamActions'

function AddTeamModal({team, companyId, setAddTeam}) {

    const [name, setName] = useState(team? team.name :'')
    const auth = useSelector(state => state.auth)

    const dispatch = useDispatch()

    const handleSubmit = async(e) =>{
        e.preventDefault()

        if(name===''){
            return window.alert("Team namd is necessary.")
        }

        // if(!team || team.name!== name){
        //     const res = await axios.get(`/team/check_name?name=${name}&id=${companyId}`,{
        //         headers : {Authorization : `Bearer ${auth.token}`}
        //     })
        //     if(res.data!=="OK"){
        //         return window.alert("Team name already exist.")
        //     }
        // }
        const teamRequest = {
            name:name,
            companyId : companyId
        }
        
        if(!team){
            dispatch(createteam(teamRequest))
        }else{
            dispatch(updateTeam(team.id, teamRequest))
        }
        setAddTeam(false)
    }
    return (
        <div className="add-modal form">
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    {
                        team
                        ? `Edit ${team.name}`
                        : 'Add Team'
                    }
                </div>

                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input type="text" className="form-control" id="name" name="name" onChange={e=>setName(e.target.value)} value={name} />
                </div>

                <div className="form-button">
                    <button type="submit" style={{marginRight:"10px"}}>Save</button>
                    <button onClick={()=> setAddTeam(false)}>Cancel</button>
                </div>
            </form>
    </div>
    )
}

export default AddTeamModal
