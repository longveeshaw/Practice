import {connect} from 'react-redux'
import Link from "../components/Link";
import PropTypes from 'prop-types'
import {setVisibilityFilter} from '../actions/index'

const mapStateToProps=(state,ownProps)=>{
    return {
        active: state.visibilityFilter===ownProps.filter
    }
}
const mapDispatchToProps=(dispatch,ownProps)=>{
    return {
        onClick: ()=>{
            dispatch(setVisibilityFilter(ownProps.filter))
        }
    }
}

const FilterLink = connect(mapStateToProps, mapDispatchToProps)(Link)

FilterLink.propTypes = {
    filter:PropTypes.string.isRequired
}

export default FilterLink