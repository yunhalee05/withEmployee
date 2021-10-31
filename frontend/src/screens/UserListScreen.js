import React, { useEffect, useState } from 'react'
import { getuserlist } from '../_actions/userActions'
import { useDispatch, useSelector } from 'react-redux'
import Loading from '../components/Loading'
import Error from '../components/Error'



function UserListScreen(props) {

    const userlist = useSelector(state => state.userlist)

    const [page, setPage] = useState(1)

    const pageRange = [...Array(userlist.totalPage).keys()]

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getuserlist(page))
    }, [dispatch, page])

    const handleClick = (id) =>{
        props.history.push(`/user/${id}`)
    }

    return (
        <div className="list">
            {
                userlist.error && <Error error = {userlist.error}/>
            }
            {
                userlist.loading && <Loading/>
            }
            <div className="list-name">
                User List
            </div>
            <div className="list-table">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>E-mail</th>
                            <th>Phone</th>
                            <th>Description</th>
                            <th>Team</th>
                            <th>Company</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            userlist.users.map((user,index)=>(
                                <tr key={index}>
                                    <td>{user.id}</td>
                                    <td onClick={()=>handleClick(user.id)}>{user.name}</td>
                                    <td>{user.email}</td>
                                    <td>{user.phoneNumber}</td>
                                    <td>{user.description}</td>
                                    <td>{user.teams.map(t=>t)}</td>
                                    <td>{user.companies.map(c=>c)}</td>
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
                            <a className="page-link" onClick={e=>setPage(userlist.totalPage)} aria-label="Next" style={{color:'black'}}>
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

export default UserListScreen
