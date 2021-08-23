import axios from 'axios';
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { createCompany } from '../_actions/companyActions';

function AddCompanyModal({setAddCompany}) {

    const auth = useSelector(state => state.auth)

    const [cName, setCName] = useState('')
    const [cDescription, setCDescription] = useState('')

    const dispatch = useDispatch()


    const handleSubmit = async(e)=>{
        e.preventDefault();

        const res = await axios.post(`/company/check_name?name=${cName}`, null)
        if(res.data ==="Duplicated"){
            window.alert('This Company name already exist.')
        }else{
            const companyDTO={
                name:cName,
                description:cDescription,
                ceoId: auth.user.id
            }

            dispatch(createCompany(companyDTO))
            setAddCompany(false)
            
        }


    }
    

    return (
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    Company
                </div>

                <div className="form-group">
                    <label htmlFor="cName">Company Name</label>
                    <input type="text" className="form-control" id="cName" name="cName" onChange={e=>setCName(e.target.value)} value={cName} />
                </div>

                <div className="form-group">
                <label htmlFor="cDescription">Description</label>
                <textarea type="text" className="form-control" id="cDescription" name="cDescription" onChange={e=>setCDescription(e.target.value)}  value={cDescription}/>
                </div>

                <div className="form-button">
                    <button type="submit" >Save</button>
                    <button onClick={()=> setAddCompany(false)}>Cancel</button>
                </div>
            </form>
        </div>
    )
}

export default AddCompanyModal
