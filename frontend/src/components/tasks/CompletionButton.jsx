import React, { useState } from 'react';
import { getTodayString } from '../../utils/dateHelpers';

const CompletionButton = ({ taskId, onComplete }) => {
    const [isCompleting, setIsCompleting] = useState(false);
    const [completionDate, setCompletionDate] = useState(getTodayString());
    const [showDatePicker, setShowDatePicker] = useState(false);

    const handleComplete = async () => {
        setIsCompleting(true);
        try {
            await onComplete(taskId, completionDate);
            setShowDatePicker(false);
        } catch (error) {
            console.error('Failed to complete task:', error);
        } finally {
            setIsCompleting(false);
        }
    };

    return (
        <div className="completion-button-container">
            {!showDatePicker ? (
                <button
                    onClick={() => setShowDatePicker(true)}
                    className="btn btn-success"
                    disabled={isCompleting}
                >
                    ✓ Mark as Completed
                </button>
            ) : (
                <div className="completion-date-picker">
                    <input
                        type="date"
                        value={completionDate}
                        onChange={(e) => setCompletionDate(e.target.value)}
                        className="date-input"
                    />
                    <button
                        onClick={handleComplete}
                        className="btn btn-success btn-small"
                        disabled={isCompleting}
                    >
                        {isCompleting ? 'Completing...' : 'Confirm'}
                    </button>
                    <button
                        onClick={() => setShowDatePicker(false)}
                        className="btn btn-secondary btn-small"
                        disabled={isCompleting}
                    >
                        Cancel
                    </button>
                </div>
            )}
        </div>
    );
};

export default CompletionButton;