import  {BrowserRouter ,Route} from 'react-router-dom'
import React, { useEffect, useState } from 'react';
import Header from './components/Header';
import CompanyScreen from './screens/CompanyListScreen';
import CompanyListScreen from './screens/CompanyListScreen';
import TeamListScreen from './screens/TeamListScreen';
import ProfileScreen from './screens/ProfileScreen';
import LoginScreen from './screens/LoginScreen';
import UserListScreen from './screens/UserListScreen';
import RegisterScreen from './screens/RegisterScreen';
import HomeScreen from './screens/HomeScreen';
import EditProfileScreen from './screens/EditProfileScreen';


function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Header/>

        <Route exact path="/login" component={LoginScreen}/>
        <Route exact path="/register" component={RegisterScreen}/>
        
        <Route exact path="/home" component={HomeScreen}/>
        <Route exact path="/company" component={CompanyListScreen}/>
        <Route exact path="/team" component={TeamListScreen}/>
        <Route exact path="/users" component={UserListScreen}/>
        <Route exact path="/user/:id" component={ProfileScreen}/>
        <Route exact path="/user/edit/:id" component={EditProfileScreen}/>
      </div>
    </BrowserRouter>
  );
}

export default App;
