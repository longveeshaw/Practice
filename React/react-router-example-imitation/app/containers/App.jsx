import React from 'react'

class App extends React.Component {

    render() {
        return (
            <div>
                <h2>I am the App template zz xx ee</h2>
                {this.props.children}
            </div>
        )
    }
}

export default App