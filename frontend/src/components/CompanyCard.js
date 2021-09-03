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
                <i class="far fa-edit"></i>
                <i class="far fa-trash-alt" onClick={handleDelete}></i>
                {/* <button onClick={handleDelete}>DELETE</button> */}
            </div>

            <div className="company-name">
                    <Link to={`/company/${company.id}`}>
                        {company.name}
                    </Link>
            </div>

            <div className="company-info">
                <div className="company-total-number">
                    <div>{company.totalNumber}</div>
                    <div style={{fontSize:'9px'}}>Members</div>
                </div>

                <div className="company-ceo">
                    <Link to={`/user/${company.ceo.id}`}>
                        <div>{company.ceo.name}</div>
                        <div>{company.ceo.email}</div>
                    </Link>
                </div>
            </div>

           <div className="company-description">
               {company.description}
           </div>

           
        </div>
    )
}

export default CompanyCard
