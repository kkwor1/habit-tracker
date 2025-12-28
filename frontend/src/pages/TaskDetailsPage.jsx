import React from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { useTaskDetails } from '../hooks/useTaskDetails';
import { useStatistics } from '../hooks/useStatistics';
import Loading from '../components/common/Loading';
import ErrorMessage from '../components/common/ErrorMessage';
import StatisticsCard from '../components/statistics/StatisticsCard';
import CompletionCalendar from '../components/statistics/CompletionCalendar';
import CompletionButton from '../components/tasks/CompletionButton';
import Button from '../components/common/Button';
import { formatDate } from '../utils/dateHelpers';
import { PRIORITY_COLORS } from '../utils/constants';
import taskApi from '../api/taskApi';

const TaskDetailsPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { task, loading: taskLoading, error: taskError } = useTaskDetails(id);
    const { statistics, loading: statsLoading, error: statsError } = useStatistics(id);

    const handleDelete = async () => {
        if (window.confirm(`Are you sure you want to delete "${task.title}"?`)) {
            try {
                await taskApi.deleteTask(id);
                navigate('/');
            } catch (err) {
                alert('Failed to delete task: ' + err.message);
            }
        }
    };

    const handleComplete = async (taskId, completionDate) => {
        try {
            await taskApi.completeTask(taskId, completionDate);
            window.location.reload(); // Refresh to show updated stats
        } catch (err) {
            alert('Failed to complete task: ' + err.message);
        }
    };

    if (taskLoading) return <Loading message="Loading task details..." />;
    if (taskError) return <ErrorMessage message={taskError} onRetry={() => window.location.reload()} />;
    if (!task) return <ErrorMessage message="Task not found" />;

    const priorityColor = PRIORITY_COLORS[task.priority];

    return (
        <div className="page-container">
            <div className="breadcrumb">
                <Link to="/" className="breadcrumb-link">Tasks</Link>
                <span className="breadcrumb-separator">/</span>
                <span className="breadcrumb-current">{task.title}</span>
            </div>

            <div className="task-details-container">
                <div className="task-details-header">
                    <div>
                        <div className="task-details-badges">
              <span
                  className="priority-badge"
                  style={{ backgroundColor: priorityColor }}
              >
                {task.priority}
              </span>
                            <span className={`status-badge ${task.active ? 'active' : 'inactive'}`}>
                {task.active ? 'Active' : 'Inactive'}
              </span>
                        </div>
                        <h1 className="task-details-title">{task.title}</h1>
                        {task.description && (
                            <p className="task-details-description">{task.description}</p>
                        )}
                    </div>

                    <div className="task-details-actions">
                        {task.active && (
                            <CompletionButton
                                taskId={task.id}
                                onComplete={handleComplete}
                            />
                        )}
                        <Link to={`/tasks/${task.id}/edit`}>
                            <Button variant="secondary">Edit Task</Button>
                        </Link>
                        <Button variant="danger" onClick={handleDelete}>
                            Delete Task
                        </Button>
                    </div>
                </div>

                <div className="task-details-grid">
                    <div className="detail-card">
                        <h3 className="detail-card-title">Task Information</h3>
                        <div className="detail-items">
                            <div className="detail-item">
                                <span className="detail-label">Daily Target:</span>
                                <span className="detail-value">{task.dailyTargetValue}</span>
                            </div>
                            <div className="detail-item">
                                <span className="detail-label">Accumulated Value:</span>
                                <span className="detail-value highlight">{task.accumulatedValue}</span>
                            </div>
                            <div className="detail-item">
                                <span className="detail-label">Start Date:</span>
                                <span className="detail-value">{formatDate(task.startDate)}</span>
                            </div>
                            <div className="detail-item">
                                <span className="detail-label">End Date:</span>
                                <span className="detail-value">{formatDate(task.endDate)}</span>
                            </div>
                            <div className="detail-item">
                                <span className="detail-label">Last Processed:</span>
                                <span className="detail-value">{formatDate(task.lastProcessedDate)}</span>
                            </div>
                        </div>
                    </div>
                </div>

                {statsLoading ? (
                    <Loading message="Loading statistics..." />
                ) : statsError ? (
                    <ErrorMessage message={statsError} onRetry={() => window.location.reload()} />
                ) : (
                    <>
                        <StatisticsCard statistics={statistics} />
                        <CompletionCalendar completedDates={statistics?.completedDates} />
                    </>
                )}
            </div>
        </div>
    );
};

export default TaskDetailsPage;