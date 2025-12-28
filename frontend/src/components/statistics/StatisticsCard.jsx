import React from 'react';

const StatisticsCard = ({ statistics }) => {
    if (!statistics) return null;

    return (
        <div className="statistics-container">
            <h2 className="statistics-title">Completion Statistics</h2>

            <div className="stats-grid">
                <div className="stat-card">
                    <div className="stat-icon">📊</div>
                    <div className="stat-content">
                        <div className="stat-value">{statistics.totalCompletions}</div>
                        <div className="stat-label">Total Completions</div>
                    </div>
                </div>

                <div className="stat-card">
                    <div className="stat-icon">📅</div>
                    <div className="stat-content">
                        <div className="stat-value">{statistics.totalPossibleDays}</div>
                        <div className="stat-label">Total Days</div>
                    </div>
                </div>

                <div className="stat-card highlight">
                    <div className="stat-icon">🎯</div>
                    <div className="stat-content">
                        <div className="stat-value">{statistics.completionRate}%</div>
                        <div className="stat-label">Completion Rate</div>
                    </div>
                </div>

                <div className="stat-card">
                    <div className="stat-icon">🔥</div>
                    <div className="stat-content">
                        <div className="stat-value">
                            {statistics.completedDates?.length || 0}
                        </div>
                        <div className="stat-label">Days Completed</div>
                    </div>
                </div>
            </div>

            {statistics.firstCompletion && (
                <div className="stats-timeline">
                    <div className="timeline-item">
                        <span className="timeline-label">First Completion:</span>
                        <span className="timeline-value">
              {new Date(statistics.firstCompletion).toLocaleDateString()}
            </span>
                    </div>
                    {statistics.lastCompletion && (
                        <div className="timeline-item">
                            <span className="timeline-label">Latest Completion:</span>
                            <span className="timeline-value">
                {new Date(statistics.lastCompletion).toLocaleDateString()}
              </span>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default StatisticsCard;