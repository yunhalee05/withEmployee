import{combineReducers, createStore, compose, applyMiddleware} from 'redux';
import thunk from 'redux-thunk'
import { authReducer } from './authReducers';
import { companylistReducer, companyReducer } from './companyReducers';
import { messageReducer } from './messageReducers';
import { socketReducer } from './socketReducers';
import { createTeamReducer, deleteTeamReducer, teamlistReducer, teamReducer } from './teamReducers';
import { adduserteamReducer, deleteuserteamReducer, userlistReducer, userReducer } from './userReducers';

const initialState={
    auth : {
        ...localStorage.getItem('auth') ? JSON.parse(localStorage.getItem('auth')):null
    }
}

const reducer = combineReducers({
    userlist : userlistReducer,
    teamlist : teamlistReducer,
    companylist : companylistReducer,
    profileuser : userReducer,
    auth : authReducer,
    team : teamReducer,
    company : companyReducer,
    createdTeam : createTeamReducer,
    deleteTeam : deleteTeamReducer,
    addUsertoTeam : adduserteamReducer,
    deleteUserFromTeam : deleteuserteamReducer,
    socket : socketReducer,
    message : messageReducer,
    

})

const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||compose;
const store = createStore(reducer, initialState, composeEnhancer(applyMiddleware(thunk)));

export default store;