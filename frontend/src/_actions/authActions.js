import axios from "axios"
import { LOGIN_FAIL, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAIL, REGISTER_REQUEST, REGISTER_SUCCESS } from "../_constants/authConstants"

export const login =({email, password}) => async(dispatch, getState)=>{
    dispatch({
        type:LOGIN_REQUEST
    })

    const body= {
        username:email,
        password:password
    }

    try{
        const res1 = await axios.post('/user/login', body)
        const res2 = await axios.post('authenticate',body)

        dispatch({
            type:LOGIN_SUCCESS,
            payload:{
                user:res1.data,
                token:res2.data
            }
        })

        localStorage.setItem("auth", JSON.stringify({user:res1.data, token:res2.data}))
    }catch(error){
        dispatch({
            type:LOGIN_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}

// export const register =(bodyFormData) => async(dispatch, getState)=>{
//     dispatch({
//         type:REGISTER_REQUEST
//     })

//     const email = bodyFormData.get('email')
//     const password = bodyFormData.get('password')

//     try{
//         const res1 = await axios.post('/user/register',bodyFormData)
//         const res2 = await axios.post('authenticate',{username:email,password:password })

//         dispatch({
//             type:REGISTER_SUCCESS,
//             payload:{
//                 user: res1.data,
//                 token:res2.data
//             }
//         })

//         localStorage.setItem("auth", JSON.stringify({user:res1.data, token:res2.data}))

//         return res1.data
        

//     }catch(error){
//         dispatch({
//             type:REGISTER_FAIL,
//             payload:
//                 error.response && error.response.data.message
//                 ? error.response.data.message
//                 : error.message
//         })
//     }
// }
export const register =(bodyFormData, email, password) => async(dispatch, getState)=>{
    dispatch({
        type:REGISTER_REQUEST
    })

    try{
        const res1 = await axios.post('/users', bodyFormData)
        const res2 = await axios.post('authenticate',{username:email,password:password })

        dispatch({
            type:REGISTER_SUCCESS,
            payload:{
                user: res1.data,
                token:res2.data
            }
        })

        localStorage.setItem("token", JSON.stringify(res2.data))
        return res1.data
    
    }catch(error){
        dispatch({
            type:REGISTER_FAIL,
            payload:
                error.response && error.response.data.message
                ? error.response.data.message
                : error.message
        })
    }
}


export const logout = () =>(dispatch)=>{
    dispatch({
        type:LOGOUT
    })

    localStorage.removeItem("auth")
}
