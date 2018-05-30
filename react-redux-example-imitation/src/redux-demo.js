import {createStore} from 'redux'

/**
 * 计数器，有增加、减少功能
 */

export default function () {
//define a reducer
    function reducer(state = 0, action) {
        switch (action.type){
            case 'DECREMENT':
                return state-1;
            case 'INCREMENT':
                return state+1;
            default:
                return state;
        }
    }
//create store
    let store =createStore(reducer);

    //subscribe log
    store.subscribe(()=>{
        console.log(store.getState())
    })

//dispatch
    store.dispatch({type: 'INCREMENT'});
    store.dispatch({type: 'INCREMENT'});
    store.dispatch({type:'DECREMENT'});
}
