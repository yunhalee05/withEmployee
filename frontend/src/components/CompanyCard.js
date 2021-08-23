import React from 'react'
import {Link} from 'react-router-dom'

function CompanyCard({company}) {
    return (
        <div className="company-card">
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
