import React from 'react';
import Header from './Header';

const Layout = ({ children }) => {
    return (
        <div className="app-container">
            <Header />
            <main className="main-content">
                {children}
            </main>
            <footer className="footer">
                <p>© Kubatov Kairat, 2025 Smart Habit Tracker - University Project</p>
            </footer>
        </div>
    );
};

export default Layout;