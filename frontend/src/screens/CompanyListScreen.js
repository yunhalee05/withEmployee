import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getcompanylist } from '../_actions/companyActions'
import {Link} from 'react-router-dom'
import Loading from '../components/Loading'

function CompanyListScreen(props) {

    const companylist = useSelector(state => state.companylist)

    const dispatch = useDispatch()

    const [page, setPage] = useState(1)
    const pageRange = [...Array(companylist.totalPage).keys()]


    useEffect(() => {
        dispatch(getcompanylist(page))
    }, [dispatch, page])


    const handleOnClick= (id)=>{
        props.history.push(`/company/${id}`)
    }

    return (
        <div className="list">
            {
                companylist.loading && <Loading/>
            }
            <div className="list-name">
                Company List
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
                            companylist.loading===false && companylist.companylist.map((company,index)=>(
                                <tr key={index}>
                                    <td>{company.id}</td>
                                    <td onClick={()=>handleOnClick(company.id)}>{company.name}</td>
                                    <td>{company.description}</td>
                                    <td>{company.ceo}</td>
                                    <td >{company.totalNumber}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>

                <nav aria-label="Page navigation example" style={{width:'100%', marginTop:"3rem"}}>
                    <ul className="pagination" style={{justifyContent:'center'}}>
                        <li className="page-item">
                            <a className="page-link" aria-label="Previous" onClick={e=>setPage(1)} style={{color:'black'}}>
                                <span aria-hidden="true">&laquo;</span>
                                <span className="sr-only">Previous</span>
                            </a>
                        </li>
                        {
                            pageRange.map(x=>(
                                <li key={x} className={`page-item ${page===x+1 && 'page-active'}`}><a className="page-link" onClick={e=>setPage(x+1)} style={{color:'black'}}>{x+1}</a></li>
                            ))
                        }
                        <li className="page-item">
                            <a className="page-link" onClick={e=>setPage(companylist.totalPage)} aria-label="Next" style={{color:'black'}}>
                                <span aria-hidden="true">&raquo;</span>
                                <span className="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
 
        </div>
    )
}

export default CompanyListScreen
