import './App.css';
import React, {useState, useEffect} from 'react';
import Header from './components/Header';
import Todos from './components/Todos';
import Modal from './components/Modal';
import { TodoEntryDto } from './generated/openapi/src/';


const App: React.FC = () => {
  const [todos, setTodos] = useState<TodoEntryDto[]>([]);

  const fetchTodos = async() => {
    const res = await fetch("http://localhost:8080/todo-controller/todos")
    const data = await res.json()
    setTodos(data)
    console.log(data)
  }

  useEffect(() => {
    fetchTodos();
  }, [])

  const [modal, setModal] = useState(false);

  const toggleModal = () => {
    setModal(!modal)
  }

  useEffect(() => {
    if(modal) {
      document.body.classList.add("active-modal")
    } else {
      document.body.classList.remove("active-modal")
    }
  }, [modal])

  return (
    <div className="App">
      <Header toggleModal={toggleModal} />
      <Todos todos={todos.sort((a, b) => {
        const aId = a.id ? a.id : 0;
        const bId = b.id ? b.id : 0;
        return aId - bId;
      })} setTodos={setTodos} toggleModal={toggleModal}/>
      {modal && <Modal setTodos={setTodos} todos={todos} toggleModal={toggleModal}/>}
    </div>
  );
}

export default App;
