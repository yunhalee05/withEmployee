import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { edituser } from '../_actions/userActions'
import { editValid } from '../utils'
import userImage from '../images/user.svg'
import axios from 'axios'
import Error from './Error'


function EditProfileModal({user, setOnEdit}) {
    
    const auth = useSelector(state => state.auth)

    const [name, setName] = useState(user.name ? user.name :'')
    const [email, setEmail] = useState(user.email ? user.email :'')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [description, setDescription] = useState(user.description ? user.description : '')
    const [imageURL, setImageURL] = useState(user.imageURL ? user.imageURL : '')
    const [phoneNumber, setPhoneNumber] = useState(user.phoneNumber ? user.phoneNumber : '')

    const [typePass, setTypePass] = useState(false)
    const [err, setErr] = useState({})

    const dispatch = useDispatch()

    const handleImage = async(e)=>{
        const file = e.target.files[0]
        const err = checkImage(file)

        if(err) return window.alert(err)
        if(file){
            var preview = document.getElementById('preview')
            preview.src = URL.createObjectURL(file)
        }
        setImageURL(file)
    }

    const checkImage = (file) =>{
        let err=""

        if(!file) return err="File does not exist."
        if(file.size>1024*1024){
            err = "The largest image size is 1mb."
        }
        if(file.type !== 'image/jpeg' && file.type !== 'image/png'){
            err = "Image format is incorrect."
        }

        return err
    }


    const handleSubmit = async(e) =>{
        e.preventDefault();
        const check = editValid(name, email, password, confirmPassword, description, phoneNumber)

        if(check.errLength===0){
            if(email !== user.email){
                const res = await axios.post(`/user/check_email?email=${email}`,null)
                if(res.data ==="Duplicated"){
                    return window.alert('This email already exist.')
                }
            }

            const bodyFormData = new FormData()
            bodyFormData.append('multipartFile', imageURL)
            bodyFormData.append('id', user.id)
            bodyFormData.append('name', name)
            bodyFormData.append('email', email)
            bodyFormData.append('password', password)
            bodyFormData.append('description', description)
            bodyFormData.append('phoneNumber', phoneNumber)
            // console.log(bodyFormData)
    
            dispatch(edituser(bodyFormData))    
            setOnEdit(false)

        }else {
            setErr(check.err)
        }

    }

    return (
        <div className="add-modal form">
            {
                auth.error && <Error error={auth.error}/>
            }
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <div className="form-name">
                    Edit {user.name} Profile
                </div>

                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input type="text" className="form-control" id="name" name="name" onChange={e=>setName(e.target.value)} required value={name}/>
                </div>
                    {
                        err.name
                        ? <small className="err">{err.name}</small>
                        : ''
                    }

                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" className="form-control" id="email" name="email" onChange={e=>setEmail(e.target.value)} required value={email}/>
                </div>
                    {
                        err.email
                        ? <small>{err.email}</small>
                        : ''
                    }

                <div className="form-group">
                    <label htmlFor="password">password</label>
                    <input type={typePass ? "text" : "password"} className="form-control" id="password" name="password" onChange={e=>setPassword(e.target.value)} value={password}/>
                    <small className="pass" onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                </div>
                    {
                        err.password
                        ? <small>{err.password}</small>
                        : ''
                    }

                <div className="form-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input type={typePass ? "text" : "password"}  className="form-control" id="confirmPassword" name="confirmPassword" onChange={e=>setConfirmPassword(e.target.value)} value={confirmPassword}/>
                    <small className="pass" onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                </div>
                    {
                        err.confirmPassword
                        ? <small>{err.confirmPassword}</small>
                        : ''
                    }

                <div className="form-group">
                    <label htmlFor="image">Image</label>
                    <img id="preview" src={user.imageUrl ? user.imageUrl: userImage} alt="imageURL" />
                    <div className="image-input">
                        <i className="fas fa-camera"></i>
                        <input type="file" className="form-control" id="file_up" name="file" accept="image/*" onChange={handleImage} />
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone</label>
                    <input type="text" className="form-control" id="phoneNumber" name="phoneNumber" onChange={e=>setPhoneNumber(e.target.value)} value={phoneNumber}/>
                </div>
                    {
                        err.phoneNumber
                        ? <small>{err.phoneNumber}</small>
                        : ''
                    }

                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <textarea type="text" className="form-control" id="description" name="name" onChange={e=>setDescription(e.target.value)} value={description}/>
                </div>
                    {
                        err.description
                        ? <small>{err.description}</small>
                        : ''
                    }

                <div className="form-button">
                    <button type="submit" >Save</button>
                    <button onClick={()=> setOnEdit(false)} style={{marginLeft:"1rem"}} >Cancel</button>
                </div>

            </form>

        </div>
    )
}

export default EditProfileModal
