export enum TodoPriority {
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM',
  LOW = 'LOW',
}

export type Todo = {
  id: number;
  task: string;
  priority: TodoPriority;
  completed: boolean;
  links: {
    self: string;
    todos: string;
  };
};
