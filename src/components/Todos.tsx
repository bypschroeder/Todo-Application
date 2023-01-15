import React from 'react'
import { TodoEntryDto } from '../generated/openapi/src/'
import Todo from './Todo'

interface TodosProps {
  todos: TodoEntryDto[]
  toggleModal: () => void
  setTodos: React.Dispatch<React.SetStateAction<TodoEntryDto[]>>
}

const Todos: React.FC<TodosProps> = (props) => {
  const todoElements = props.todos.map(todo => {
    return <Todo key={todo.id} id={todo.id} title={todo.title!} description={todo.description} completed={todo.completed!} toggleModal={props.toggleModal} setTodos={props.setTodos} />
  })


  return (
    <div>
      {todoElements}
    </div>
  )
}

export default Todos