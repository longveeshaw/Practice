import React from 'react'
import {render} from 'react-dom'
import RouterMap from './router/routerMap'
import {hashHistory} from 'react-router'
import './static/css/common.less'

render(
    <RouterMap history={hashHistory}/>
    , document.getElementById('root')
)
