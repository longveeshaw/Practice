import {connect} from 'react-redux'
import TodoList from "../components/TodoList";
import {toggleTodo} from '../actions/index.js';

const getVisibleTodos = (todos, filter) => {
    switch (filter) {
        case 'SHOW_ALL':
            return todos;
        case 'SHOW_COMPLETED':
            return todos.filter(i => i.completed);
        case 'SHOW_ACTIVE':
            return todos.filter(i => !i.completed)
        default:
            throw  new Error("unexpected filter " + filter);
    }
}

const mapStateToProps = (state) => {
    return {
        todos: getVisibleTodos(state.todos, state.visibilityFilter)
    }
}
const mapDispatchToProps = {
    onTodoClick: toggleTodo
}
const VisibleTodoList = connect(
    mapStateToProps, mapDispatchToProps
)(TodoList)

export default VisibleTodoList