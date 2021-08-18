import{combineReducers, createStore, compose, applyMiddleware} from 'redux';
import thunk from 'redux-thunk'
import { teamlistReducer } from './teamReducers';
import { userlistReducer } from './userReducers';

const initialState={

}

const reducer = combineReducers({
    userlist : userlistReducer,
    teamlist : teamlistReducer,

})

const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||compose;
const store = createStore(reducer, initialState, composeEnhancer(applyMiddleware(thunk)));

export default store;