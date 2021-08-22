import React, { useEffect, useState } from 'react'
import {Link} from 'react-router-dom'
import { register } from '../_actions/authActions'
import { useDispatch, useSelector } from 'react-redux'
import axios from 'axios'
import { valid } from '../utils'
import { createCompany } from '../_actions/companyActions'


function RegisterScreen(props) {

    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [description, setDescription] = useState('')
    const [imageURL, setImageURL] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    const [ceo, setCeo] = useState(false)

    const [cName, setCName] = useState('')
    const [cDescription, setCDescription] = useState('')

    const [err, setErr] = useState({})

    const [typePass, setTypePass] = useState(false)
    const [cpTypePass, setCpTypePass] = useState(false)

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
            const companyres = await axios.post(`/company/check_name?name=${cName}`, null)
            if(res.data ==="Duplicated"){
                window.alert('This email already exist.')
            }else if(companyres.data ==="Duplicated"){
                window.alert('This Company name already exist.')
            }else{
                const userDTO = {
                    name: name, 
                    email : email, 
                    password :password,
                    description: description,
                    imageURL : imageURL,
                    phoneNumber : phoneNumber,
                    role : ceo? "CEO" : "Member"
                }
                dispatch(register(userDTO)).then(res=> {

                    if(ceo){
                        const companyDTO={
                            name:cName,
                            description:cDescription,
                            ceoId: res.id
                        }
    
                        dispatch(createCompany(companyDTO))
                    }

                })

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
                    <small className="pass" onClick={()=>setTypePass(!typePass)}>
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
                    <input type={cpTypePass ? "text" : "password"}  className="form-control" id="confirmPassword" name="confirmPassword" onChange={e=>setConfirmPassword(e.target.value)}  value={confirmPassword}/>
                    <small className="pass" onClick={()=>setCpTypePass(!cpTypePass)}>
                        {cpTypePass? 'Hide' : 'Show'}
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
                    <textarea type="text" className="form-control" id="description" name="description" onChange={e=>setDescription(e.target.value)}  value={description}/>
                    {
                        err.description
                        ? <small>{err.description}</small>
                        : ''
                    }
                </div>

                <div className="form-group">
                    <span>Are you a CEO ? </span>
                    <input type="checkbox" onClick={()=>setCeo(!ceo)}/>
                </div>
                

                {
                    ceo &&
                    <div>
                        <div className="form-name">
                            Company
                        </div>

                        <div className="form-group">
                            <label htmlFor="cName">Company Name</label>
                            <input type="text" className="form-control" id="cName" name="cName" onChange={e=>setCName(e.target.value)} value={cName} />
                        </div>

                        <div className="form-group">
                        <label htmlFor="cDescription">Description</label>
                        <textarea type="text" className="form-control" id="cDescription" name="cDescription" onChange={e=>setCDescription(e.target.value)}  value={cDescription}/>
                        </div>
                    </div>

                }


                <div className="form-button">
                    <button type="submit" >Register</button>
                </div>

                <div className="form-switch">
                    <strong>Already have an account? <Link to="/login">Login Now</Link></strong>
                </div>

            </form>

        </div>
    )
}

export default RegisterScreen
