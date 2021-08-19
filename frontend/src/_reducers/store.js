import{combineReducers, createStore, compose, applyMiddleware} from 'redux';
import thunk from 'redux-thunk'
import { companylistReducer } from './companyReducers';
import { teamlistReducer } from './teamReducers';
import { userlistReducer, userReducer } from './userReducers';

const initialState={

}

const reducer = combineReducers({
    userlist : userlistReducer,
    teamlist : teamlistReducer,
    companylist : companylistReducer,
    user : userReducer,

})

const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||compose;
const store = createStore(reducer, initialState, composeEnhancer(applyMiddleware(thunk)));

export default store;