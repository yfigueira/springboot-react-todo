import React from 'react';
import { RiEditCircleLine } from 'react-icons/ri';
import { CiCirclePlus, CiCircleRemove, CiCircleCheck } from 'react-icons/ci';

export enum ButtonType {
  CREATE,
  UPDATE,
  DELETE,
  COMPLETE,
}

export enum ButtonSize {
  LARGE = 'h-14 w-14',
  MEDIUM = 'h-8 w-8',
  SMALL = 'h-6 w-6',
}

interface ButtonProps {
  type: ButtonType;
  size: ButtonSize;
  classes?: string;
  onClick: () => void;
}

const Button: React.FC<ButtonProps> = ({ type, classes, size, onClick }) => {
  let buttonStyle: (size: ButtonSize) => any;

  switch (type) {
    case ButtonType.CREATE:
      buttonStyle = createButton;
      break;
    case ButtonType.UPDATE:
      buttonStyle = updateButton;
      break;
    case ButtonType.DELETE:
      buttonStyle = deleteButton;
      break;
    case ButtonType.COMPLETE:
      buttonStyle = completeButton;
  }

  const handleClick = () => {
    onClick();
  };

  return (
    <button
      className={`rounded-full hover:opacity-50 ${classes}`}
      onClick={handleClick}>
      {buttonStyle(size)}
    </button>
  );
};

const createButton = (size: ButtonSize) => {
  return <CiCirclePlus className={`text-green-400 ${size}`} />;
};

const updateButton = (size: ButtonSize) => {
  return <RiEditCircleLine className={`text-amber-400 ${size}`} />;
};

const deleteButton = (size: ButtonSize) => {
  return <CiCircleRemove className={`text-red-400 ${size}`} />;
};

const completeButton = (size: ButtonSize) => {
  return <CiCircleCheck className={`text-blue-400 ${size}`} />;
};

export default Button;
