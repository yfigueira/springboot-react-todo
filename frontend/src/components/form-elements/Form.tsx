import React, { ReactNode } from 'react';

interface FormProps {
  children: ReactNode;
  classes?: string;
  onSubmit?: () => void;
}

const Form: React.FC<FormProps> = ({ children, classes, onSubmit }) => {
  return (
    <form
      className={classes}
      onSubmit={(event) => {
        event.preventDefault();
        if (onSubmit) {
          onSubmit();
        }
      }}>
      {React.Children.map(children, (child) => (
        <div>{child}</div>
      ))}
    </form>
  );
};

export default Form;
