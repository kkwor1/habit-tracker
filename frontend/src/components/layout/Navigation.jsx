import React from 'react';
import { Link, useLocation } from 'react-router-dom';

const Navigation = () => {
    const location = useLocation();

    const isActive = (path) => {
        return location.pathname === path ? 'active' : '';
    };

    return (
        <nav className="sidebar-nav">
            <Link to="/" className={`nav-item ${isActive('/')}`}>
                <span className="nav-icon">🏠</span>
                <span>All Tasks</span>
            </Link>
            <Link to="/tasks/create" className={`nav-item ${isActive('/tasks/create')}`}>
                <span className="nav-icon">➕</span>
                <span>Create Task</span>
            </Link>
        </nav>
    );
};

export default Navigation;