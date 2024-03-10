import React from 'react';
import { Button, ButtonType, ButtonSize } from './form-elements';

interface TodoEditsProps {
  onEnableEdit: () => void;
  onDelete: () => void;
  onCheckCompleted: () => void;
}

const TodoEdits: React.FC<TodoEditsProps> = ({
  onEnableEdit,
  onDelete,
  onCheckCompleted,
}) => {
  return (
    <div className="my-1 mx-16 p-1 flex flex-row gap-8">
      <Button
        type={ButtonType.COMPLETE}
        size={ButtonSize.SMALL}
        onClick={onCheckCompleted}></Button>
      <Button
        type={ButtonType.UPDATE}
        size={ButtonSize.SMALL}
        onClick={onEnableEdit}></Button>
      <Button
        type={ButtonType.DELETE}
        size={ButtonSize.SMALL}
        onClick={onDelete}></Button>
    </div>
  );
};

export default TodoEdits;
