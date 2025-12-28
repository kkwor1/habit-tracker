import React from 'react';

const Button = ({
                    children,
                    variant = 'primary',
                    size = 'medium',
                    disabled = false,
                    onClick,
                    type = 'button',
                    fullWidth = false
                }) => {
    const className = `btn btn-${variant} btn-${size} ${fullWidth ? 'btn-full-width' : ''}`;

    return (
        <button
            type={type}
            className={className}
            onClick={onClick}
            disabled={disabled}
        >
            {children}
        </button>
    );
};

export default Button;