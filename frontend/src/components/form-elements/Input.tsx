import React from 'react';

interface InputProps {
  inputValue: string;
  classes?: string;
  placeholder?: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input: React.FC<InputProps> = ({
  inputValue,
  classes,
  placeholder,
  onChange,
}) => {
  return (
    <input
      className={classes}
      type="text"
      value={inputValue}
      onChange={onChange}
      placeholder={placeholder}
    />
  );
};

export default Input;
