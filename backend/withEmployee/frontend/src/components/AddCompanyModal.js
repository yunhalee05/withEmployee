import axios from 'axios';
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { createCompany, editCompany } from '../_actions/companyActions';

function AddCompanyModal({setAddCompany, company}) {

    const auth = useSelector(state => state.auth)

    const [cName, setCName] = useState(company? company.name : '')
    const [cDescription, setCDescription] = useState(company? company.description:'')

    const dispatch = useDispatch()


    const handleSubmit = async(e)=>{
        e.preventDefault();

        if(cName===''){
            return window.alert('Company name is necessary.')
        }


        // if(!company || cName!==company.name){
        //     const res = await axios.post(`/company/check_name?name=${cName}`, null)
        //     if(res.data ==="Duplicated"){
        //         return window.alert('This Company name already exist.')
        //     }
        // }
        const companyRequest={
            name:cName,
            description:cDescription,
            ceoId: auth.user.id
        }

        if(!company){
            dispatch(createCompany(companyRequest))


        }else{
            dispatch(editCompany(company.id, companyRequest))

        }
        setAddCompany(false)
    }
    

    return (
        <div className="add-modal form">
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    {
                        company
                        ? `Edit ${company.name}`
                        : 'Add Company'
                    }
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
                    <button type="submit"style={{marginRight:"10px"}} >Save</button>
                    <button onClick={()=> setAddCompany(false)}>Cancel</button>
                </div>
            </form>
        </div>
    )
}

export default AddCompanyModal
