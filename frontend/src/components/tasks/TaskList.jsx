import React from 'react';
import TaskCard from './TaskCard';

const TaskList = ({ tasks, onDelete, onComplete }) => {
    if (tasks.length === 0) {
        return (
            <div className="empty-state">
                <div className="empty-icon">📝</div>
                <h3>No tasks found</h3>
                <p>Create your first task to get started!</p>
            </div>
        );
    }

    return (
        <div className="task-grid">
            {tasks.map(task => (
                <TaskCard
                    key={task.id}
                    task={task}
                    onDelete={onDelete}
                    onComplete={onComplete}
                />
            ))}
        </div>
    );
};

export default TaskList;