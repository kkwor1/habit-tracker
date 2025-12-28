import React from 'react';
import { useParams } from 'react-router-dom';
import TaskForm from '../components/tasks/TaskForm';
import { useTaskDetails } from '../hooks/useTaskDetails';
import Loading from '../components/common/Loading';
import ErrorMessage from '../components/common/ErrorMessage';
import taskApi from '../api/taskApi';

const EditTaskPage = () => {
    const { id } = useParams();
    const { task, loading, error } = useTaskDetails(id);

    const handleSubmit = async (taskData) => {
        await taskApi.updateTask(id, taskData);
    };

    if (loading) return <Loading message="Loading task..." />;
    if (error) return <ErrorMessage message={error} />;
    if (!task) return <ErrorMessage message="Task not found" />;

    return (
        <div className="page-container">
            <div className="page-header">
                <h1 className="page-title">Edit Task</h1>
                <p className="page-subtitle">Update task details and settings</p>
            </div>

            <div className="form-container">
                <TaskForm
                    initialData={task}
                    onSubmit={handleSubmit}
                    isEdit={true}
                />
            </div>
        </div>
    );
};

export default EditTaskPage;