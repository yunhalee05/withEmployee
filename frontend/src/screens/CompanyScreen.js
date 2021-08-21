import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getcompany } from '../_actions/companyActions'

function CompanyScreen(props) {

    const id = props.match.params.id

    const company = useSelector(state => state.company)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getcompany({id}))
    }, [dispatch])

    return (
        
        <div className="company-screen">
            {
                company.company &&
            <div className="company-card">
                <div className="company-card-name">
                    {company.company.name}
                </div>

                <div className="company-card-description">
                    {company.company.description}
                </div>

                <div className="company-card-teams">
                    {
                        company.company.teams.map((team,index)=>(
                            <div className="company-card-team" key={index}>
                                <span>{team.name}</span>
                            </div>
                        ))
                    }
                </div>

                <div className="company-card-users">
                    <div>{company.company.users.length}</div>
                    {
                        company.company.users.map((user, index)=>(
                            <div className="company-card-user" key={index}>
                                <span>{user}</span>
                            </div>
                        ))
                    }
                </div>
            </div>
            }
        </div>
    )
}

export default CompanyScreen
