import React from 'react';
import {IndexRoute, Route, Router} from 'react-router';
import Home from '../containers/Home'
import Detail from '../containers/Detail'
import List from '../containers/List'
import App from '../containers/App'


class RouterMap extends React.Component {
    updateHandler() {
        console.log("a router changed ")
    }

    render() {
        return (
            <Router history={this.props.history} onUpdate={this.updateHandler.bind(this)}>
                <Route path='/' component={App}>
                    <IndexRoute component={Home}/>
                    <Route path='list' component={List}/>
                    <Route path='detail/:id' component={Detail}/>
                </Route>
            </Router>
        )
    }
}

export default RouterMap