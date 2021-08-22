import  {BrowserRouter ,Route} from 'react-router-dom'
import React, { useEffect, useState } from 'react';
import Header from './components/Header';
import CompanyScreen from './screens/CompanyScreen';
import CompanyListScreen from './screens/CompanyListScreen';
import TeamListScreen from './screens/TeamListScreen';
import ProfileScreen from './screens/ProfileScreen';
import LoginScreen from './screens/LoginScreen';
import UserListScreen from './screens/UserListScreen';
import RegisterScreen from './screens/RegisterScreen';
import HomeScreen from './screens/HomeScreen';
import EditProfileScreen from './screens/EditProfileScreen';
import PrivateRouter from './customRouter/PrivateRouter';
import { useSelector } from 'react-redux';
import TeamScreen from './screens/TeamScreen';
import UserTeamScreen from './screens/UserTeamScreen';


function App() {

  const auth = useSelector(state => state.auth)

  return (
    <BrowserRouter>
      <div className="App">
        {
          auth.user &&
            <Header/>
        }

        <Route exact path="/" component={LoginScreen}/>
        <Route exact path="/login" component={LoginScreen}/>
        <Route exact path="/register" component={RegisterScreen}/>
        
        <PrivateRouter exact path="/home" component={HomeScreen}/>

        <PrivateRouter exact path="/company" component={CompanyListScreen}/>
        <PrivateRouter exact path="/team" component={TeamListScreen}/>
        <PrivateRouter exact path="/users" component={UserListScreen}/>

        <PrivateRouter exact path="/user/:id" component={ProfileScreen}/>
        <PrivateRouter exact path="/user/edit/:id" component={EditProfileScreen}/>

        <PrivateRouter exact path="/company/:id" component={CompanyScreen}/>
        <PrivateRouter exact path="/team/:id" component={TeamScreen}/>
        <PrivateRouter exact path="/teams/:id" component={UserTeamScreen}/>
        
      </div>
    </BrowserRouter>
  );
}

export default App;
