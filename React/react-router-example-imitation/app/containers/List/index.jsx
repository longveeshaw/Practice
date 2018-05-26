import React from 'react';
import {hashHistory} from 'react-router'

class List extends React.Component {

    render() {
        const arr = [1, 2, 3, 4];
        return (
            <ul>
                {
                    arr.map((item, index) => {
                        return (
                            <li key={index} onClick={this.clickHandler.bind(this,index)}>js jump to {item}</li>
                        )
                    })
                }
            </ul>

        )
    }

    clickHandler(index) {
        hashHistory.push('/detail/'+index)
    }
}
export default List