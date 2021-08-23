import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'


function EditProfileScreen(props) {

    const id = props.match.params.id

    const auth = useSelector(state => state.auth)
    const {user} = auth

    
    const [name, setName] = useState(user.name ? user.name :'')
    const [email, setEmail] = useState(user.email ? user.email :'')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [description, setDescription] = useState(user.description ? user.description : '')
    const [imageURL, setImageURL] = useState(user.imageURL ? user.imageURL : '')
    const [phoneNumber, setPhoneNumber] = useState(user.phoneNumber ? user.phoneNumber : '')

    const [typePass, setTypePass] = useState(false)


    const handleSubmit = (e) =>{
        e.preventDefault();

    }

    return (
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="form-name">
                    Edit {id}
                </div>

                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input type="text" className="form-control" id="name" name="name" onChange={e=>setName(e.target.value)} required value={name}/>
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

                <div className="form-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input type={typePass ? "text" : "password"}  className="form-control" id="confirmPassword" name="confirmPassword" onChange={e=>setConfirmPassword(e.target.value)} required value={confirmPassword}/>
                    <small className="pass" onClick={()=>setTypePass(!typePass)}>
                        {typePass? 'Hide' : 'Show'}
                    </small>
                </div>

                <div className="form-group">
                    <label htmlFor="image">Image</label>
                    <input type="text" className="form-control" id="imageUrl" name="imageUrl" onChange={e=>setImageURL(e.target.value)} required value={imageURL}/>
                </div>


                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone</label>
                    <input type="text" className="form-control" id="phoneNumber" name="phoneNumber" onChange={e=>setPhoneNumber(e.target.value)} required value={phoneNumber}/>
                </div>

                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <textarea type="text" className="form-control" id="description" name="name" onChange={e=>setDescription(e.target.value)} required value={description}/>
                </div>

                <div className="form-button">
                    <button type="submit" >Save</button>
                </div>
            </form>

        </div>
    )
}

export default EditProfileScreen
