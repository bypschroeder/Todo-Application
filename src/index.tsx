import React, { useState } from 'react';
import ReactDOM from 'react-dom/client';
import { TodoItemsApi } from './generated/openapi/src/';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root')!);

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

const todoItemsAPI = new TodoItemsApi()
export default todoItemsAPI
