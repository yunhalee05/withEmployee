import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import AddCompanyModal from '../components/AddCompanyModal'
import CompanyCard from '../components/CompanyCard'
import { getcompanies } from '../_actions/companyActions'

function CeoCompanyScreen(props) {

    const id = props.match.params.id

    // const [companies, setCompanies] = useState([])
    const company = useSelector(state => state.company)

    const [addCompany, setAddCompany] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
       dispatch(getcompanies({id}))

    }, [dispatch])
    
    return (
        <div>
            {(company.loading===false && company.companies) &&
                <div className="user-team">
                    <div className="company-card-container">
                        {
                            company.companies.map((company,index)=>(
                                <CompanyCard company={company} key={index}/>
                            ))
                        }

                        <div className="card-button">
                            <button onClick={()=>setAddCompany(!addCompany)}>ADD COMPANY</button>
                        </div>
                    </div>

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
