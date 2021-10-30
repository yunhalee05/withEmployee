import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import Loading from '../components/Loading'
import { getteamlist } from '../_actions/teamActions'

function TeamListScreen() {

    const teamlist = useSelector(state => state.teamlist)
    const dispatch = useDispatch()

    const [page, setPage] = useState(1)
    const pageRange = [...Array(teamlist.totalPage).keys()]


    useEffect(() => {
        dispatch(getteamlist(page))
    }, [dispatch, page])

    return (
        <div className="list" style={{maxWidth:"40rem"}}>
            {
                teamlist.loading && <Loading/>
            }
            <div className="list-name">
                Team List
            </div>
            <div className="list-table">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Company</th>
                            <th>Total Member</th>

                        </tr>
                    </thead>

                    <tbody>
                        {
                            teamlist.teams.map((team,index)=>(
                                <tr key={index}>
                                    <td>{team.name}</td>
                                    <td>{team.company}</td>
                                    <td>{team.totalNumber}</td>
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
                            <a className="page-link" onClick={e=>setPage(teamlist.totalPage)} aria-label="Next" style={{color:'black'}}>
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

export default TeamListScreen
