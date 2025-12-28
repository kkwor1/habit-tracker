import React from 'react';
import { createBrowserRouter } from 'react-router-dom';
import Layout from './components/layout/Layout';
import HomePage from './pages/HomePage';
import TaskDetailsPage from './pages/TaskDetailsPage';
import CreateTaskPage from './pages/CreateTaskPage';
import EditTaskPage from './pages/EditTaskPage';
import NotFoundPage from './pages/NotFoundPage';

export const router = createBrowserRouter([
    {
        path: '/',
        element: <Layout><HomePage /></Layout>,
    },
    {
        path: '/tasks/create',
        element: <Layout><CreateTaskPage /></Layout>,
    },
    {
        path: '/tasks/:id',
        element: <Layout><TaskDetailsPage /></Layout>,
    },
    {
        path: '/tasks/:id/edit',
        element: <Layout><EditTaskPage /></Layout>,
    },
    {
        path: '*',
        element: <Layout><NotFoundPage /></Layout>,
    },
]);