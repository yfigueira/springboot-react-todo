import { useEffect } from 'react';
import TodoCreate from './components/TodoCreate';
import TodoTable from './components/TodoTable';
import useFetch from './hooks/useFetch';

const API_URL = 'http://localhost:8080/api/todos';

function App() {
  const { todos, fetchTodos, addTodo } = useFetch();

  useEffect(() => {
    fetchTodos(API_URL);
  }, [todos]);

  return (
    <div>
      <TodoTable todos={todos} />
      <div className="flex justify-center">
        <div className="fixed bottom-8 w-3/4 m-auto">
          <TodoCreate addTodo={addTodo} />
        </div>
      </div>
    </div>
  );
}

export default App;
