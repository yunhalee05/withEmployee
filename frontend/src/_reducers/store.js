import{combineReducers, createStore, compose, applyMiddleware} from 'redux';
import thunk from 'redux-thunk'
import { authReducer } from './authReducers';
import { companylistReducer, companyReducer } from './companyReducers';
import { messageReducer } from './messageReducers';
import { socketReducer } from './socketReducers';
import { teamlistReducer, teamReducer } from './teamReducers';
import { userlistReducer, userReducer } from './userReducers';

const initialState={
}

const reducer = combineReducers({
    userlist : userlistReducer,
    teamlist : teamlistReducer,
    companylist : companylistReducer,
    profileuser : userReducer,
    auth : authReducer,
    team : teamReducer,
    company : companyReducer,
    socket : socketReducer,
    message : messageReducer,
})

const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||compose;
const store = createStore(reducer, initialState, composeEnhancer(applyMiddleware(thunk)));

export default store;