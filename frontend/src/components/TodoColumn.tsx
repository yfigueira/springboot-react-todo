import React from 'react';
import { Todo } from '../model/Todo';
import TodoItem from './TodoItem';

interface TodoColumnProps {
  title: string;
  todos: Todo[];
  headerColor?: string;
}

const TodoColumn: React.FC<TodoColumnProps> = ({
  todos,
  title,
  headerColor,
}) => {
  return (
    <div className="m-2 justify-center items-center">
      <h1 className={`text-center text-6xl font m-10 ${headerColor}`}>
        {title}
      </h1>
      {todos.map((t) => (
        <TodoItem key={t.id} todo={t} />
      ))}
    </div>
  );
};

export default TodoColumn;
