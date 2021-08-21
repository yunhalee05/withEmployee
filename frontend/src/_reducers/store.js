import{combineReducers, createStore, compose, applyMiddleware} from 'redux';
import thunk from 'redux-thunk'
import { authReducer } from './authReducers';
import { companylistReducer } from './companyReducers';
import { teamlistReducer } from './teamReducers';
import { userlistReducer, userReducer } from './userReducers';

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

})

const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||compose;
const store = createStore(reducer, initialState, composeEnhancer(applyMiddleware(thunk)));

export default store;