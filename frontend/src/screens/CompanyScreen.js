import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import TeamCard from '../components/TeamCard'
import UserCard from '../components/UserCard'
import { getcompany } from '../_actions/companyActions'

function CompanyScreen(props) {

    const id = props.match.params.id

    const company = useSelector(state => state.company)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getcompany({id}))
    }, [dispatch])

    return (
        
        <div className="user-team">
            {
                company.company &&
                <div>
                <div className="company-card">
                    <div className="company-card-name">
                        {company.company.name}
                    </div>

                    <div className="company-card-description">
                        {company.company.description}
                    </div>



                </div>

            
                <div className="team-card-container">
                {
                    company.company.teams.map((team, index)=>(
                        
                        <TeamCard team={team} key={index}/>
                    ))
                }

                </div>


                <div className="user-card-container" >
                    {
                        company.company.members.map((user, index)=>(
                            <UserCard user={user} key={index}/>
                        ))
                        
                    }
                </div>
                </div>
            }
        </div>
    )
}

export default CompanyScreen
