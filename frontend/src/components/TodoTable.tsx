import { Todo, TodoPriority } from '../model/Todo';
import TodoColumn from './TodoColumn';

interface TodoTableProps {
  todos: Todo[];
}

const TodoTable: React.FC<TodoTableProps> = ({ todos }) => {
  const lowPriorities = todos.filter((t) => t.priority === TodoPriority.LOW);
  const mediumPriorities = todos.filter(
    (t) => t.priority === TodoPriority.MEDIUM
  );
  const highPriorities = todos.filter((t) => t.priority === TodoPriority.HIGH);

  return (
    <div className="flex flex-row justify-between p-2 m-2">
      <TodoColumn
        todos={highPriorities}
        title={'Now!'}
        headerColor="text-red-400"
      />
      <TodoColumn
        todos={mediumPriorities}
        title={'Soon'}
        headerColor="text-amber-400"
      />
      <TodoColumn
        todos={lowPriorities}
        title={'Can wait...'}
        headerColor="text-green-400"
      />
    </div>
  );
};

export default TodoTable;
