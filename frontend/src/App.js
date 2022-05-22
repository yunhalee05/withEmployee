import  {BrowserRouter ,Route} from 'react-router-dom'
import React, { useEffect } from 'react';
import Header from './components/Header';
import CompanyScreen from './screens/CompanyScreen';
import CompanyListScreen from './screens/CompanyListScreen';
import TeamListScreen from './screens/TeamListScreen';
import ProfileScreen from './screens/ProfileScreen';
import LoginScreen from './screens/LoginScreen';
import UserListScreen from './screens/UserListScreen';
import RegisterScreen from './screens/RegisterScreen';
import HomeScreen from './screens/HomeScreen';
import PrivateRouter from './customRouter/PrivateRouter';
import CeoRouter from './customRouter/CeoRouter';
import { useDispatch, useSelector } from 'react-redux';
import TeamScreen from './screens/TeamScreen';
import UserTeamScreen from './screens/UserTeamScreen';
import CeoCompanyScreen from './screens/CeoCompanyScreen';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs'
import { SOCKET } from "./_constants/socketConstants";
import SocketClient from './SocketClient';
import axios from 'axios';
import { LOGIN_SUCCESS } from './_constants/authConstants';
import { useState } from 'react';



function App() {

  const auth = useSelector(state => state.auth)
  const socket = useSelector(state => state.socket)

  var sock = new SockJS('http://localhost:8080/chat')
  let client = Stomp.over(sock);

  const dispatch = useDispatch()

  const [isAuth, setIsAuth] = useState(false)

  useEffect(async() => {
    if (!auth.user && localStorage.getItem("token") != null) {
      const res = await axios.get(`/login?token=${localStorage.getItem("token")}`)
      dispatch({
        type:LOGIN_SUCCESS,
        payload:res.data
      })
      setIsAuth(true)
    }

    if (!auth.user && localStorage.getItem("token") == null) {
      setIsAuth(false)
    }
  }, [])
  

  useEffect(() => {
    if(auth.user){
      dispatch({
        type:SOCKET,
        payload:{client}
      })
    }

  }, [auth.user])

  return (
    <BrowserRouter>
      {
        socket.client && <SocketClient/>
      }
  
      <Header/>
      <div className="App">

        <Route exact path="/" component={HomeScreen}/>
        <Route exact path="/home" component={HomeScreen}/>
        <Route exact path="/login" component={LoginScreen}/>
        <Route exact path="/register" component={RegisterScreen}/>

        {
          isAuth &&
          <div>
            <PrivateRouter exact path="/user/:id" component={ProfileScreen}/>
            <PrivateRouter exact path="/company/:id" component={CompanyScreen}/>
            <PrivateRouter exact path="/team/:id" component={TeamScreen}/>
            <PrivateRouter exact path="/teams/:id" component={UserTeamScreen}/>
            <CeoRouter exact path="/companies/:id" component={CeoCompanyScreen}/>
            <PrivateRouter exact path="/company" component={CompanyListScreen}/>
            <PrivateRouter exact path="/team" component={TeamListScreen}/>
            <PrivateRouter exact path="/users" component={UserListScreen}/>
          </div>
        }
        
      </div>
    </BrowserRouter>
  );
}

export default App;
