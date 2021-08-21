import React, { useEffect, useState } from 'react'
import {Link} from 'react-router-dom'
import { register } from '../_actions/authActions'
import { useDispatch, useSelector } from 'react-redux'
import axios from 'axios'
import { valid } from '../utils'


function RegisterScreen(props) {

    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [description, setDescription] = useState('')
    const [imageURL, setImageURL] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    const [err, setErr] = useState({})

    const [typePass, setTypePass] = useState(false)

    const auth = useSelector(state => state.auth)
    const dispatch = useDispatch()


    useEffect(() => {
        if(auth.user){
            props.history.push('/')
        }
    }, [])

    const handleSubmit = async(e) =>{
        e.preventDefault();

        const check = valid(name, email, password, confirmPassword, description, phoneNumber)


        if(check.errLength===0){
            const res = await axios.post(`/user/check_email?email=${email}`,null)
            if(res.data ==="Duplicated"){
                window.alert('This email already exist.')
            }else{
                dispatch(register({name, email, password, description, imageURL, phoneNumber}))
            }
        }else {
            setErr(check.err)
        }

    }

    const validateEmail = (email)=>{
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    return (
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    Register
                </div>

                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input type="text" className="form-control" id="name" name="name" onChange={e=>setName(e.target.value)} value={name} />
                    {
                        err.name
                        ? <small>{err.name}</small>
                        : ''
                    }
                </div>

                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" className="form-control" id="email" name="email" onChange={e=>setEmail(e.target.value)}  value={email}/>
                    {
                        err.email
                        ? <small>{err.email}</small>
                        : ''
                    }
                </div>

                <div className="form-group">
                    <label htmlFor="password">password</label>
                    <input type={typePass ? "text" : "password"} className="form-control" id="password" name="password" onChange={e=>setPassword(e.target.value)}  value={password}/>
                    <small onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                    {
                        err.password
                        ? <small>{err.password}</small>
                        : ''
                    }
                </div>

                <div className="form-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input type={typePass ? "text" : "password"}  className="form-control" id="confirmPassword" name="confirmPassword" onChange={e=>setConfirmPassword(e.target.value)}  value={confirmPassword}/>
                    <small onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                    {
                        err.confirmPassword
                        ? <small>{err.confirmPassword}</small>
                        : ''
                    }
                </div>


                <div className="form-group">
                    <label htmlFor="image">Image</label>
                    <input type="text" className="form-control" id="imageUrl" name="imageUrl" onChange={e=>setImageURL(e.target.value)}  value={imageURL}/>
                </div>


                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone</label>
                    <input type="text" className="form-control" id="phoneNumber" name="phoneNumber" onChange={e=>setPhoneNumber(e.target.value)}  value={phoneNumber}/>
                    {
                        err.phoneNumber
                        ? <small>{err.phoneNumber}</small>
                        : ''
                    }
                </div>

                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <textarea type="text" className="form-control" id="description" name="name" onChange={e=>setDescription(e.target.value)}  value={description}/>
                    {
                        err.description
                        ? <small>{err.description}</small>
                        : ''
                    }
                </div>


                <button type="submit" className="form-button">Register</button>
            </form>

            <div className="form-switch">
                <strong>Already have an account? <Link to="/login">Login Now</Link></strong>
            </div>
        </div>
    )
}

export default RegisterScreen
