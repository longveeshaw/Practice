const todos = (state = [], action) => {
    switch (action.type) {
        case 'TOGGLE_TODO':
            return state.map(todo => {
                return (todo.id === action.id) ? {...todo, completed: !todo.completed} : todo
            })
        case 'ADD_TODO':
            return [...state, {
                id: action.id,
                completed: false,
                text: action.text
            }]
        default:
            return state
    }
}
export default todos