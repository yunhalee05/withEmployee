import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getcompanylist } from '../_actions/companyActions'
import {Link} from 'react-router-dom'

function CompanyListScreen() {

    const companylist = useSelector(state => state.companylist)

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getcompanylist())
    }, [dispatch])


    return (
        <div className="list">
            <div className="list-name">
                CompanyList
            </div>
            <div className="list-table">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>CEO</th>
                            <th>Total Member</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            companylist && companylist.companies.map((company,index)=>(
                                <tr key={index}>
                                    <td>{company.id}</td>
                                    <Link to={`/company/${company.id}`}><td>{company.name}</td></Link>
                                    <td>{company.description}</td>
                                    <td>{company.ceo.name}</td>
                                    <td >{company.totalNumber}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            
        </div>
    )
}

export default CompanyListScreen
