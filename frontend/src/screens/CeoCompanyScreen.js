import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import AddCompanyModal from '../components/AddCompanyModal'
import CompanyCard from '../components/CompanyCard'
import { getcompanies } from '../_actions/companyActions'
import Loading from '../components/Loading'
import Error from '../components/Error'

function CeoCompanyScreen(props) {

    const id = props.match.params.id
    const company = useSelector(state => state.company)

    const [addCompany, setAddCompany] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
        if(!company.companies || (company.companies[0] && (company.companies[0].ceo.id != id))) {
            dispatch(getcompanies({id}))
        }
    }, [id, company.companies])
    
    return (
        <div>
            {
                company.error &&<Error error={company.error}/>
            }
            {
                company.loading && <Loading/>
            }
            {(company.loading===false && company.companies) &&
                <div className="user-team">
                    <div className="company-card-container">
                        {
                            company.companies.map((company,index)=>(
                                <CompanyCard company={company} key={index} />
                            ))
                        }

                        <div className="company-add-button" onClick={()=>setAddCompany(!addCompany)}>
                            <i className="far fa-plus-square fa-2x"></i>
                            <div>ADD COMPANY</div>
                        </div>
                    </div>

                </div>
            }
            {
                (company.loading===false && company.companies) && (company.companies.length ==0) && 
                <div className='profile-userteam-title'>
                    You dont't belong to any team yet.
                </div>
            }
            {
                addCompany &&
                <AddCompanyModal setAddCompany={setAddCompany} />
            }
        </div>
    )
}

export default CeoCompanyScreen
