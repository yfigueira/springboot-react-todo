import React from 'react';
import { TodoPriority } from '../../model/Todo';

interface RadioButtonProps {
  inputValue: TodoPriority;
  group: string;
  classes?: string;
  checked: boolean;
  onClick: (priority: TodoPriority) => void;
}

export enum RadioButtonType {
  DANGER = 'bg-red-200 checked:bg-red-600 checked:border-2 checked:border-red-400',
  WARNING = 'bg-amber-200 checked:bg-amber-600 checked:border-2 checked:border-amber-400',
  SUCCESS = 'bg-green-200 checked:bg-green-600 checked:border-2 checked:border-green-400',
}

const RadioButton: React.FC<RadioButtonProps> = ({
  inputValue,
  group,
  classes,
  checked,
  onClick,
}) => {
  return (
    <input
      type="radio"
      value={inputValue}
      name={group}
      checked={checked}
      onChange={() => {
        onClick(inputValue);
      }}
      className={`h-5 w-5 appearance-none rounded-full cursor-pointer ${classes}`}
    />
  );
};

export default RadioButton;
