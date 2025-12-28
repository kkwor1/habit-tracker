import { useState, useEffect } from 'react';
import taskApi from '../api/taskApi';

/**
 * Custom hook for fetching a single task's details
 */
export const useTaskDetails = (taskId) => {
    const [task, setTask] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTask = async () => {
            if (!taskId) return;

            try {
                setLoading(true);
                setError(null);
                const data = await taskApi.getTaskById(taskId);
                setTask(data);
            } catch (err) {
                setError(err.response?.data?.message || 'Failed to fetch task details');
                console.error('Error fetching task:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchTask();
    }, [taskId]);

    return { task, loading, error };
};