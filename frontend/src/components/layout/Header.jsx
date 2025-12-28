import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <header className="header">
            <div className="header-content">
                <Link to="/" className="logo">
                    <span className="logo-icon">✓</span>
                    <span className="logo-text">Smart Habit Tracker</span>
                </Link>
                <nav className="header-nav">
                    <Link to="/" className="nav-link">Home</Link>
                    <Link to="/tasks/create" className="nav-link nav-link-primary">
                        + New Task
                    </Link>
                </nav>
            </div>
        </header>
    );
};

export default Header;