import { useState } from 'react';
import { Todo, TodoPriority } from '../model/Todo';
import { get, post, del, put } from '../api/api';

const API_URL = 'http://localhost:8080/api/todos';

const useFetch = () => {
  const [todos, setTodos] = useState<Todo[]>([]);

  const fetchTodos = (url: string): void => {
    get(url).then((res) => {
      const result = res.map(
        (t: {
          id: string;
          task: string;
          priority: TodoPriority;
          completed: boolean;
          links: any;
        }) => ({
          id: t.id,
          task: t.task,
          priority: t.priority,
          completed: t.completed,
          links: {
            self: t.links[0].href,
            todos: t.links[1].href,
          },
        })
      );
      setTodos(result);
    });
  };

  const addTodo = (data: {}): void => {
    post(API_URL, data).then((res) => {
      const newTodo = {
        id: res.id,
        task: res.task,
        priority: res.priority,
        completed: res.completed,
        links: {
          self: res._links.self.href,
          todos: res._links.todos.href,
        },
      };

      setTodos([...todos, newTodo]);
    });
  };

  const updateTodo = (url: string, data: {}): void => {
    put(url, data).then((res) => {
      const updatedTodos = todos.map((todo) => {
        return todo.id === res.id
          ? {
              ...todo,
              task: res.task,
              priority: res.priority,
            }
          : todo;
      });

      setTodos(updatedTodos);
    });
  };

  const deleteTodo = (url: string): void => {
    del(url).then(() => fetchTodos(API_URL));
  };

  return { todos, fetchTodos, addTodo, deleteTodo, updateTodo };
};

export default useFetch;
