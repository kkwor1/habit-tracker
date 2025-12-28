import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../components/common/Button';

const NotFoundPage = () => {
    return (
        <div className="page-container">
            <div className="not-found-container">
                <div className="not-found-icon">404</div>
                <h1 className="not-found-title">Page Not Found</h1>
                <p className="not-found-message">
                    The page you're looking for doesn't exist or has been moved.
                </p>
                <Link to="/">
                    <Button variant="primary">Go Back Home</Button>
                </Link>
            </div>
        </div>
    );
};

export default NotFoundPage;