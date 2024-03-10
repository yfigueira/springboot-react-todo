import React from 'react';

interface TodoTaskReadonlyProps {
  value: string;
  lineThrough?: boolean;
}

const TodoTaskReadonly: React.FC<TodoTaskReadonlyProps> = ({
  value,
  lineThrough,
}) => {
  return (
    <div className="flex flex-row gap-10 items-center justify-center mb-2 p-3">
      <h1 className={`ubuntu-regular text-xl ${lineThrough && 'line-through'}`}>
        {value}
      </h1>
    </div>
  );
};

export default TodoTaskReadonly;
