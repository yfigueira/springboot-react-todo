import React, { useRef } from 'react';
import { TodoPriority } from '../model/Todo';
import { Form, RadioSwitch } from './form-elements';
import TodoTaskInput from './TodoTaskInput';

interface TodoCreateProps {
  addTodo: (data: {}) => void;
}

const TodoCreate: React.FC<TodoCreateProps> = ({ addTodo }) => {
  const task = useRef('');
  const priority = useRef(TodoPriority.LOW);

  const handleSubmit = () => {
    addTodo({
      task: task.current,
      priority: priority.current,
      completed: false,
    });

    task.current = '';
    priority.current = TodoPriority.LOW;
  };

  return (
    <div className="border-2 border-grey shadow rounded-full bg-zinc-50">
      <Form onSubmit={handleSubmit}>
        <RadioSwitch
          group={'priority'}
          checked={priority.current}
          onChange={(newPriority: TodoPriority) => {
            priority.current = newPriority;
          }}></RadioSwitch>
        <TodoTaskInput
          task={task.current}
          onInputChange={(event) => (task.current = event.target.value)}
          onClick={handleSubmit}></TodoTaskInput>
      </Form>
    </div>
  );
};

export default TodoCreate;
