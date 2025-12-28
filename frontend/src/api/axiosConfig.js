import axios from 'axios';

/**
 * Axios instance configured for the Spring Boot API
 * Base URL points to the backend server
 */
const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


// Request interceptor for logging and token injection (if needed)
axiosInstance.interceptors.request.use(
    (config) => {
        // Build a clear full URL for logging without using URL() which can drop path segments
        try {
            const base = config.baseURL || '';
            const strip = (s = '') => s.replace(/\/$/, '');
            const full = base.startsWith('http')
                ? strip(base) + (config.url.startsWith('/') ? config.url : '/' + config.url)
                : window.location.origin + strip(base) + (config.url.startsWith('/') ? config.url : '/' + config.url);

            console.log('API Request:', config.method.toUpperCase(), full);

            // Warn if call targets backend but misses the '/api' prefix (only for absolute backend host)
            if (full.includes('localhost:8080') && !full.includes('/api/')) {
                console.warn('Request to backend missing /api prefix:', full);
            }
        } catch (e) {
            console.log('API Request:', config.method.toUpperCase(), config.url);
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor for error handling
axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        console.error('API Error:', error.response?.data || error.message);

        // Handle specific error codes
        if (error.response?.status === 404) {
            console.error('Resource not found');
        } else if (error.response?.status === 400) {
            console.error('Bad request:', error.response.data);
        } else if (error.response?.status === 500) {
            console.error('Server error');
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;