import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {Link} from 'react-router-dom'
import Error from '../components/Error'
import Loading from '../components/Loading'
import { login } from '../_actions/authActions'

function LoginScreen(props) {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const [typePass, setTypePass] = useState(false)

    const auth = useSelector(state => state.auth)

    const dispatch = useDispatch()

    useEffect(() => {
        if(auth.user && auth.user.id){
            props.history.push('/home')
        }
    }, [auth.user])

    const handleSubmit= (e) =>{
        e.preventDefault();
        dispatch(login({email, password}))
    }

    return (
        <div className="form">
            {
               auth.error && <Error error={auth.error}/>
            }
            {
                auth.loading && <Loading/>
            }
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    Login
                </div>


                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" className="form-control" id="email" name="email" onChange={e=>setEmail(e.target.value)} required value={email}/>
                </div>

                <div className="form-group">
                    <label htmlFor="password">password</label>
                    <input type={typePass ? "text" : "password"} className="form-control" id="password" name="password" onChange={e=>setPassword(e.target.value)} required value={password}/>
                    <small className="pass" onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                </div>

                <div className="form-button">
                    <button type="submit">Sign in</button>
                </div>

                <div className="form-switch">
                    <strong>Don't you have an account yet? <Link to="/register">Register Now</Link></strong>
                </div>
            </form>

        </div>
    )
}

export default LoginScreen
