import React from 'react'
import { useDispatch } from 'react-redux'
import {Link} from 'react-router-dom'
import { deleteCompany } from '../_actions/companyActions'

function CompanyCard({company}) {

    const dispatch = useDispatch()

    const handleDelete = () =>{

        if(window.confirm('Are you Sure to delete this company? This action is irreversible.')){
            dispatch(deleteCompany(company.id))
        }
    }
    return (
        <div className="company-card">
            <div className="company-delete-button">
                <button onClick={handleDelete}>DELETE</button>
            </div>
           <div className="company-name">
                <Link to={`/company/${company.id}`}>
                    {company.name}
                </Link>
           </div>
           
           <div className="company-ceo">
               <Link to={`/user/${company.ceo.id}`}>
                    <span>{company.ceo.name}</span>
                    <span>{company.ceo.email}</span>
               </Link>
           </div>

           <div className="company-total-number">
               <span>Total Company Members</span>
               <span>{company.totalNumber}</span>
           </div>

           <div className="company-description">
               {company.description}
           </div>

           
        </div>
    )
}

export default CompanyCard
