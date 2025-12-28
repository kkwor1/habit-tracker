import { useState, useEffect, useCallback } from 'react';
import taskApi from '../api/taskApi';

/**
 * Custom hook for managing tasks
 * Provides tasks list, loading state, and CRUD operations
 */
export const useTasks = () => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchAllTasks = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await taskApi.getAllTasks();
      setTasks(data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch tasks');
      console.error('Error fetching tasks:', err);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchAllTasks();
  }, [fetchAllTasks]);

  const deleteTask = async (id) => {
    try {
      await taskApi.deleteTask(id);
      setTasks(prev => prev.filter(task => task.id !== id));
      return { success: true };
    } catch (err) {
      const errorMessage = err.response?.data?.message || 'Failed to delete task';
      setError(errorMessage);
      return { success: false, error: errorMessage };
    }
  };

  const completeTask = async (taskId, completionDate) => {
    try {
      await taskApi.completeTask(taskId, completionDate);
      
      // If completing for today, remove from current view
      const today = new Date().toISOString().split('T')[0];
      if (completionDate === today) {
        setTasks(prev => prev.filter(task => task.id !== taskId));
      } else {
        // Otherwise just refresh to update accumulated values
        await fetchAllTasks();
      }
      
      return { success: true };
    } catch (err) {
      const errorMessage = err.response?.data?.message || 'Failed to complete task';
      setError(errorMessage);
      return { success: false, error: errorMessage };
    }
  };

  const filterByPriority = async (priority) => {
    try {
      setLoading(true);
      setError(null);
      // If asking for all or missing priority, just refetch all tasks
      if (!priority || String(priority).toUpperCase() === 'ALL') {
        await fetchAllTasks();
        return;
      }

      try {
        const data = await taskApi.getTasksByPriority(priority);
        setTasks(data);
      } catch (apiErr) {
        // Fallback to client-side filter if API fails
        console.warn('Priority filter API failed, falling back to client-side filter', apiErr);
        setTasks(prev => prev.filter(t => String(t.priority).toUpperCase() === String(priority).toUpperCase()));
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to filter tasks');
      console.error('Filter error:', err);
    } finally {
      setLoading(false);
    }
  };

  const getActiveTasks = async (date) => {
    try {
      setLoading(true);
      setError(null);
      try {
        const data = await taskApi.getActiveTasksByDate(date);
        setTasks(data);
      } catch (apiErr) {
        console.warn('Active tasks API failed, falling back to client-side filter', apiErr);
        // Fallback: fetch full list then filter by date range
        const all = await taskApi.getAllTasks();
        const target = date || new Date().toISOString().split('T')[0];
        const filtered = all.filter(t => {
          const start = t.startDate ? t.startDate.split('T')[0] : null;
          const end = t.endDate ? t.endDate.split('T')[0] : null;
          return (!start || start <= target) && (!end || end >= target);
        });
        setTasks(filtered);
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch active tasks');
      console.error('Active tasks error:', err);
    } finally {
      setLoading(false);
    }
  };

  const sortByPriority = async () => {
    try {
      setLoading(true);
      setError(null);
      try {
        const data = await taskApi.getTasksSortedByPriority();
        setTasks(data);
      } catch (apiErr) {
        console.warn('Sort API failed, falling back to client-side sort', apiErr);
        // Ensure we sort the full list (not only currently visible tasks)
        const all = await taskApi.getAllTasks();
        const priorityOrder = { HIGH: 3, MEDIUM: 2, LOW: 1 };
        all.sort((a, b) => (priorityOrder[String(b.priority).toUpperCase()] || 0) - (priorityOrder[String(a.priority).toUpperCase()] || 0));
        setTasks(all);
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to sort tasks');
      console.error('Sort error:', err);
    } finally {
      setLoading(false);
    }
  };

  const processDailyRollover = async () => {
    try {
      setLoading(true);
      setError(null);
      try {
        await taskApi.processDailyRollover();
        // Refresh from server
        await fetchAllTasks();
      } catch (apiErr) {
        console.warn('Rollover API failed, applying client-side increment', apiErr);
        // Fallback: increment accumulatedValue by dailyTargetValue for each task
        setTasks(prev => prev.map(t => ({
          ...t,
          accumulatedValue: (Number(t.accumulatedValue) || 0) + (Number(t.dailyTargetValue) || 0)
        })));
      }
      return { success: true };
    } catch (err) {
      const errorMessage = err.response?.data?.message || 'Failed to process rollover';
      setError(errorMessage);
      return { success: false, error: errorMessage };
    } finally {
      setLoading(false);
    }
  };

  return {
    tasks,
    loading,
    error,
    refetch: fetchAllTasks,
    deleteTask,
    completeTask,
    filterByPriority,
    getActiveTasks,
    sortByPriority
    ,
    processDailyRollover
  };
};