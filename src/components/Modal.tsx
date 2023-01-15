import React, { useEffect } from 'react'
import { useState } from 'react'
import todoItemsAPI from '..'
import { TodoEntryDto } from '../generated/openapi/src'
import ErrorMessage from '../components/ErrorMessage'
import { createGlobalState } from "react-use"


interface ModalProps {
  setTodos: React.Dispatch<React.SetStateAction<TodoEntryDto[]>>
  todos: TodoEntryDto[]
  toggleModal: () => void
}

const Modal: React.FC<ModalProps> = (props) => {

  const [todo, setTodo] = useState<TodoEntryDto>()
  const [title, setTitle] = useState<string | undefined>("")
  const [description, setDescription] = useState<string | undefined>("")
  const [completed, setCompleted] = useState<boolean | undefined>(false)
  const [error, setError] = useState<boolean | undefined>(false)
  

  const handleTitleChange: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    setTitle(event.target.value)
  }

  const handleDescriptionChange: React.ChangeEventHandler<HTMLTextAreaElement> = (event) => {
    setDescription(event.target.value)
  }

  const handleCompletedChange: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    setCompleted(event.target.checked)
  }

  useEffect(() => {
    const searchParams = new URLSearchParams(window.location.search)
    const id = searchParams.get("id")
    if (!id) return;
    const currentTodo = props.todos.find(todo => todo.id === parseInt(id))
    if (currentTodo === undefined) {
      return
    }
    setTitle(currentTodo.title)
    setDescription(currentTodo.description)
    setCompleted(currentTodo.completed)
  }, [props.todos])

  useEffect(() => {
    setTodo({title, description, completed})
  }, [title, description, completed])

  const handleSubmit: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault();
    const searchParams = new URLSearchParams(window.location.search)
    const id = searchParams.get("id")
    if(todo === undefined || todo === null) return
    if(id) {
      putData(todo, parseInt(id)).then(newTodo => props.setTodos(prev => {
        const newTodos = prev.filter(todo => todo.id !== newTodo.id)
        return newTodos.concat([newTodo])
      }))
    } else {
     postData(todo).then(newTodo => props.setTodos(prev => {
      if (newTodo === undefined) return prev;
      else return prev.concat([newTodo])
    }))
    }
      handleClose();
  }  

  function handleClose() {
    setTitle("");
    setDescription("");
    setCompleted(false);
    props.toggleModal();
  }

  const handleError = () => {
    setError(!error)
  }

  async function postData(todo: TodoEntryDto) {
    try {
      return await todoItemsAPI.createTodo({todoEntryDto: todo})
    } catch (e) {
      console.error(e)
    }
  }

  async function putData(todo: TodoEntryDto, id: number) {
    return await todoItemsAPI.updateTodo({id, title: todo.title, description: todo.description, completed: todo.completed})
    // const url = `http://localhost:8080/todo-controller/todo/${id}?title=${encodeURIComponent(title ? title : "")}&description=${encodeURIComponent(description ? description : "")}&completed=${encodeURIComponent(completed ? completed : "")}`
    // const response = await fetch(url, {
    //   method: "PUT",
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(todo),
    // })
    // return await response.json()
  }

  return (
      <div className='modal'>
      <form className='modal-content'>
        <div className='close-modal-wrapper'> 
          <button className='close-modal' onClick={handleClose}>Close</button>
        </div>
        <div className='modal-title'>
          <label htmlFor="title">Title</label>
          <input type="text" name='title' id='title' onChange={handleTitleChange} value={title}/>
        </div>
        <div className='modal-description'>
          <label htmlFor="description">Description</label>
          <textarea name="description" id="description" cols={30} rows={5} onChange={handleDescriptionChange} value={description}></textarea>
        </div>
        <div className='modal-completed'>
          <label htmlFor="completed">Completed</label>
          <input type="checkbox" name="completed" id="completed" onChange={handleCompletedChange} checked={completed} />
        </div>
        <div className="submit-modal-wrapper">
          <button type='submit' className='submit-modal' onClick={handleSubmit} >Submit</button>
        </div>
        {error && <ErrorMessage handleError={handleError} />}
      </form>
    </div>
  )
}

export default Modal