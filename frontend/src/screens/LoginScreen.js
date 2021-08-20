import React, { useState } from 'react'
import {Link} from 'react-router-dom'

function LoginScreen() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const [typePass, setTypePass] = useState(false)

    const handleSubmit= (e) =>{
        e.preventDefault();
    }
    return (
        <div className="form">
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
                    <small onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                </div>

                <button type="submit" className="form-button">Login</button>
            </form>

            <div className="form-switch">
                <strong>Don't you have an account yet? <Link to="/register">Register Now</Link></strong>
            </div>
        </div>
    )
}

export default LoginScreen
