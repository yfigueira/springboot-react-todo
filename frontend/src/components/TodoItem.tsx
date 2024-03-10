import React, { useRef, useState } from 'react';
import { Todo, TodoPriority } from '../model/Todo';
import { RadioSwitch, Form } from './form-elements';
import TodoEdits from './TodoEdits';
import TodoTaskInput from './TodoTaskInput';
import TodoTaskReadonly from './TodoTaskReadonly';
import useFetch from '../hooks/useFetch';

interface TodoItemProps {
  todo: Todo;
}

const TodoItem: React.FC<TodoItemProps> = ({ todo }) => {
  const [completed, setCompleted] = useState(todo.completed);
  const [editable, setEditable] = useState(false);

  const task = useRef(todo.task);

  const { deleteTodo, updateTodo } = useFetch();

  const handleSubmit = () => {
    updateTodo(todo.links.self, {
      ...todo,
      task: task.current,
    });
  };

  const handlePriorityChange = (priority: TodoPriority) => {
    updateTodo(todo.links.self, {
      ...todo,
      priority: priority,
    });
  };

  const handleCompleted = () => {
    const updated = !completed;

    updateTodo(todo.links.self, {
      ...todo,
      completed: updated,
    });

    setCompleted(updated);
  };

  return (
    <div className="border-2 border-grey shadow rounded-full bg-zinc-50 m-4">
      <Form>
        <div className="flex flex-row justify-between">
          <RadioSwitch
            group={'priority'}
            onChange={handlePriorityChange}
            checked={todo.priority}
          />
          {
            <TodoEdits
              onEnableEdit={() => setEditable(!editable)}
              onDelete={() => deleteTodo(todo.links.self)}
              onCheckCompleted={handleCompleted}
            />
          }
        </div>
        <div>
          {editable ? (
            <TodoTaskInput
              task={task.current}
              onInputChange={(event) => (task.current = event.target.value)}
              onClick={() => {
                handleSubmit();
                setEditable(false);
              }}
            />
          ) : (
            <TodoTaskReadonly value={task.current} lineThrough={completed} />
          )}
        </div>
      </Form>
    </div>
  );
};

export default TodoItem;
