import React from 'react';
import TaskForm from '../components/tasks/TaskForm';
import taskApi from '../api/taskApi';

const CreateTaskPage = () => {
    const handleSubmit = async (taskData) => {
        await taskApi.createTask(taskData);
    };

    return (
        <div className="page-container">
            <div className="page-header">
                <h1 className="page-title">Create New Task</h1>
                <p className="page-subtitle">
                    Set up a new habit or task to track daily
                </p>
            </div>

            <div className="form-container">
                <TaskForm onSubmit={handleSubmit} isEdit={false} />
            </div>
        </div>
    );
};

export default CreateTaskPage;