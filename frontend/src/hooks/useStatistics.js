import { useState, useEffect } from 'react';
import taskApi from '../api/taskApi';

/**
 * Custom hook for fetching task statistics
 */
export const useStatistics = (taskId) => {
    const [statistics, setStatistics] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStatistics = async () => {
            if (!taskId) return;

            try {
                setLoading(true);
                setError(null);
                const data = await taskApi.getTaskStatistics(taskId);
                setStatistics(data);
            } catch (err) {
                setError(err.response?.data?.message || 'Failed to fetch statistics');
                console.error('Error fetching statistics:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchStatistics();
    }, [taskId]);

    return { statistics, loading, error };
};