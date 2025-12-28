import React from 'react';

const ErrorMessage = ({ message, onRetry }) => {
    return (
        <div className="error-container">
            <div className="error-icon">⚠️</div>
            <h3 className="error-title">Oops! Something went wrong</h3>
            <p className="error-message">{message}</p>
            {onRetry && (
                <button onClick={onRetry} className="btn btn-primary">
                    Try Again
                </button>
            )}
        </div>
    );
};

export default ErrorMessage;