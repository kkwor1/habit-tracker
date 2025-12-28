import axiosInstance from './axiosConfig';

/**
 * API service for task-related operations
 * Corresponds to TaskController endpoints in Spring Boot
 */
const taskApi = {
    // Get all tasks
    getAllTasks: async () => {
        const response = await axiosInstance.get('/tasks');
        return response.data;
    },

    // Get tasks sorted by priority
    getTasksSortedByPriority: async () => {
        const response = await axiosInstance.get('/tasks/sorted-by-priority');
        return response.data;
    },

    // Get active tasks by date
    getActiveTasksByDate: async (date = null) => {
        const url = date
            ? `/tasks/active?date=${date}`
            : '/tasks/active';
        const response = await axiosInstance.get(url);
        return response.data;
    },

    // Get tasks by priority
    getTasksByPriority: async (priority) => {
        // If priority is missing or explicitly 'ALL', return all tasks
        if (!priority || String(priority).toUpperCase() === 'ALL') {
            const resp = await axiosInstance.get('/tasks');
            return resp.data;
        }
        const response = await axiosInstance.get(`/tasks/by-priority?priority=${encodeURIComponent(priority)}`);
        return response.data;
    },

    // Get task by ID
    getTaskById: async (id) => {
        const response = await axiosInstance.get(`/tasks/${id}`);
        return response.data;
    },

    // Create new task
    createTask: async (taskData) => {
        const response = await axiosInstance.post('/tasks', taskData);
        return response.data;
    },

    // Update task
    updateTask: async (id, taskData) => {
        const response = await axiosInstance.put(`/tasks/${id}`, taskData);
        return response.data;
    },

    // Delete task
    deleteTask: async (id) => {
        const response = await axiosInstance.delete(`/tasks/${id}`);
        return response.data;
    },

    // Mark task as completed
    completeTask: async (taskId, completionDate) => {
        const response = await axiosInstance.post('/tasks/complete', {
            taskId,
            completionDate
        });
        return response.data;
    },

    // Get task statistics
    getTaskStatistics: async (id) => {
        const response = await axiosInstance.get(`/tasks/${id}/statistics`);
        return response.data;
    },

    // Trigger daily rollover
    processDailyRollover: async () => {
        const response = await axiosInstance.post('/tasks/process-daily-rollover');
        return response.data;
    }
};

export default taskApi;