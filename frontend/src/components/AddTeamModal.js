import axios from 'axios'
import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import { getcompany } from '../_actions/companyActions'
import { createteam } from '../_actions/teamActions'

function AddTeamModal({companyId, setAddTeam}) {

    const [name, setName] = useState('')

    const dispatch = useDispatch()

    const handleSubmit = async(e) =>{
        e.preventDefault()

        const res = await axios.get(`/team/check_name?name=${name}&id=${companyId}`)
        
        if(res.data==="OK"){
            const teamDTO = {
                name:name,
                companyId : companyId
            }

            dispatch(createteam(teamDTO)).then(res=>{
                const id = res
                dispatch(getcompany({id}))
                setAddTeam(false)
            })
        }
    }
    return (
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    Add Team
                </div>

                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input type="text" className="form-control" id="name" name="name" onChange={e=>setName(e.target.value)} value={name} />
                </div>

                <div className="form-button">
                    <button type="submit" >Save</button>
                    <button onClick={()=> setAddTeam(false)}>Cancel</button>
                </div>
            </form>
    </div>
    )
}

export default AddTeamModal
