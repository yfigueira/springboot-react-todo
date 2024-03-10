import React from 'react';
import { RadioButton, RadioButtonType } from '.';
import { TodoPriority } from '../../model/Todo';

interface RadioSwitchProps {
  classes?: string;
  group: string;
  checked: TodoPriority;
  onChange: (value: TodoPriority) => void;
}

const RadioSwitch: React.FC<RadioSwitchProps> = ({
  group,
  onChange,
  checked,
}) => {
  return (
    <div className="my-1 mx-16 p-1 flex flex-row gap-8">
      <RadioButton
        classes={RadioButtonType.DANGER}
        inputValue={TodoPriority.HIGH}
        group={group}
        checked={checked === TodoPriority.HIGH}
        onClick={() => {
          onChange(TodoPriority.HIGH);
        }}></RadioButton>
      <RadioButton
        classes={RadioButtonType.WARNING}
        inputValue={TodoPriority.MEDIUM}
        group={group}
        checked={checked === TodoPriority.MEDIUM}
        onClick={() => {
          onChange(TodoPriority.MEDIUM);
        }}></RadioButton>
      <RadioButton
        classes={RadioButtonType.SUCCESS}
        inputValue={TodoPriority.LOW}
        group={group}
        checked={checked === TodoPriority.LOW}
        onClick={() => {
          onChange(TodoPriority.LOW);
        }}></RadioButton>
    </div>
  );
};

export default RadioSwitch;
