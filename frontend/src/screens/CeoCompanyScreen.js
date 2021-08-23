import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import CompanyCard from '../components/CompanyCard'
import { getcompanies } from '../_actions/companyActions'

function CeoCompanyScreen(props) {

    const id = props.match.params.id

    const [companies, setCompanies] = useState([])

    const dispatch = useDispatch()

    useEffect(() => {
       dispatch(getcompanies({id})).then(res=>{
            setCompanies(res)
       })
    }, [dispatch])
    
    return (
        <div className="user-team">
            <div className="company-card-container">
                {
                    companies.map((company,index)=>(
                        <CompanyCard company={company} key={index}/>
                    ))
                }
            </div>
        </div>
    )
}

export default CeoCompanyScreen
