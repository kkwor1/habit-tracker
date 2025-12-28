import React from 'react';
import { Link } from 'react-router-dom';
import { formatDate } from '../../utils/dateHelpers';
import { PRIORITY_COLORS } from '../../utils/constants';

const TaskCard = ({ task, onDelete, onComplete }) => {
  const priorityColor = PRIORITY_COLORS[task.priority];
  
  const handleDelete = async (e) => {
    e.preventDefault();
    if (window.confirm(`Are you sure you want to delete "${task.title}"?`)) {
      const result = await onDelete(task.id);
      if (result.success) {
        // Task removed from list by parent component
      }
    }
  };
  
  const handleComplete = async (e) => {
    e.preventDefault();
    const today = new Date().toISOString().split('T')[0];
    const result = await onComplete(task.id, today);
    
    if (result.success) {
      // Show success message
      alert('Task completed successfully!');
    } else if (result.error) {
      alert(result.error);
    }
  };
  
  return (
    <div className="task-card">
      <div className="task-card-header">
        <div className="task-priority" style={{ backgroundColor: priorityColor }}>
          {task.priority}
        </div>
        <div className={`task-status ${task.active ? 'active' : 'inactive'}`}>
          {task.active ? 'Active' : 'Inactive'}
        </div>
      </div>
      
      <Link to={`/tasks/${task.id}`} className="task-card-link">
        <h3 className="task-title">{task.title}</h3>
        {task.description && (
          <p className="task-description">{task.description}</p>
        )}
      </Link>
      
      <div className="task-metrics">
        <div className="metric">
          <span className="metric-label">Daily Target:</span>
          <span className="metric-value">{task.dailyTargetValue}</span>
        </div>
        <div className="metric">
          <span className="metric-label">Accumulated:</span>
          <span className="metric-value highlight">{task.accumulatedValue}</span>
        </div>
      </div>
      
      <div className="task-dates">
        <div className="date-item">
          <span className="date-label">Start:</span>
          <span className="date-value">{formatDate(task.startDate)}</span>
        </div>
        <div className="date-item">
          <span className="date-label">End:</span>
          <span className="date-value">{formatDate(task.endDate)}</span>
        </div>
      </div>
      
      <div className="task-actions">
        {task.active && (
          <button onClick={handleComplete} className="btn btn-success btn-small">
            ✓ Complete Today
          </button>
        )}
        <Link to={`/tasks/${task.id}/edit`} className="btn btn-secondary btn-small">
          Edit
        </Link>
        <button onClick={handleDelete} className="btn btn-danger btn-small">
          Delete
        </button>
      </div>
    </div>
  );
};

export default TaskCard;