import React from 'react';
import { Input, Button, ButtonType, ButtonSize } from './form-elements';

interface TodoTaskInputProps {
  task: string;
  onInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onClick: () => void;
}

const TodoTaskInput: React.FC<TodoTaskInputProps> = ({
  task,
  onInputChange,
  onClick,
}) => {
  return (
    <div className="flex flex-row gap-10 items-center justify-center mb-2 p-3">
      <Input
        classes="border-grey-100 border-2 rounded-full p-3 w-3/4"
        inputValue={task}
        placeholder="Add task..."
        onChange={onInputChange}></Input>
      <Button
        type={ButtonType.CREATE}
        size={ButtonSize.LARGE}
        onClick={onClick}></Button>
    </div>
  );
};

export default TodoTaskInput;
