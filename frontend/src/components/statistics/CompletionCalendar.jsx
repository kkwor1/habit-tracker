import React from 'react';

const CompletionCalendar = ({ completedDates }) => {
    if (!completedDates || completedDates.length === 0) {
        return (
            <div className="calendar-empty">
                <p>No completions yet. Start completing tasks to see your progress!</p>
            </div>
        );
    }

    const sortedDates = [...completedDates].sort((a, b) =>
        new Date(b) - new Date(a)
    );

    return (
        <div className="completion-calendar">
            <h3 className="calendar-title">Completion History</h3>
            <div className="calendar-list">
                {sortedDates.map((date, index) => (
                    <div key={index} className="calendar-item">
                        <span className="calendar-icon">✓</span>
                        <span className="calendar-date">
              {new Date(date).toLocaleDateString('en-US', {
                  weekday: 'short',
                  year: 'numeric',
                  month: 'short',
                  day: 'numeric'
              })}
            </span>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CompletionCalendar;