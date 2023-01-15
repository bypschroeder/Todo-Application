import React from "react";
import todoItemsAPI from "..";
import { TodoEntryDto } from "../generated/openapi/src/";

interface TodoProps {
  id?: number;
  title: string;
  description?: string;
  completed: boolean;
  setTodos: React.Dispatch<React.SetStateAction<TodoEntryDto[]>>;
  toggleModal: () => void;
}

const Todo: React.FC<TodoProps> = (props) => {
  async function updateTodo(completed: boolean) {
    // const response = await fetch(`http://localhost:8080/todo-controller/todo/${props.id}?completed=${completed}`, {method: "PUT"});
    // const newUpdateTodo = await response.json();
    const newUpdateTodo = await todoItemsAPI.updateTodo({
      id: props.id!,
      completed,
    });
    props.setTodos((prev) => {
      const newTodos = prev.filter((todo) => todo.id !== newUpdateTodo.id);
      return newTodos.concat([newUpdateTodo]);
    });
  }

  async function deleteTodo(id: number) {
    // await fetch(`http://localhost:8080/todo-controller/todo/${props.id}`, {method: "DELETE"});
    await todoItemsAPI.deleteTodo({ id });
    props.setTodos((prev) => {
      return prev.filter((todo) => todo.id !== props.id);
    });
  }

  const handleClick: React.MouseEventHandler<HTMLInputElement> = (e) => {
    updateTodo((e.target as HTMLInputElement).checked);
  };

  const handleDelete: React.MouseEventHandler<HTMLButtonElement> = () => {
    deleteTodo(props.id!);
  };

  const handleEdit = () => {
    window.history.pushState({ id: props.id }, "a", `?id=${props.id}`);
    props.toggleModal();
  };

  return (
    <div className="todo">
      <h1 className="todo-title">{props.title}</h1>
      <p className="todo-description">{props.description}</p>
      <div className="todo-clickable">
        <input
          className="todo-completed"
          type="checkbox"
          checked={props.completed}
          onClick={handleClick}
        />
        <div className="todo-buttons">
          <button className="todo-edit-button" onClick={handleEdit}>
            Edit
          </button>
          <button className="todo-delete-button" onClick={handleDelete}>
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export default Todo;
