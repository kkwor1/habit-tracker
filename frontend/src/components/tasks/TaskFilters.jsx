import React, { useState } from 'react';
import { PRIORITY_LEVELS } from '../../utils/constants';
import { getTodayString } from '../../utils/dateHelpers';

const TaskFilters = ({ onFilterChange, onShowActive, onSortByPriority, onResetFilters }) => {
  const [selectedPriority, setSelectedPriority] = useState('ALL');
  const [activeDate, setActiveDate] = useState(getTodayString());
  
  const handlePriorityChange = (priority) => {
    setSelectedPriority(priority);
    if (priority === 'ALL') {
      onResetFilters();
    } else {
      onFilterChange(priority);
    }
  };
  
  const handleShowActive = () => {
    onShowActive(activeDate);
    setSelectedPriority('ALL'); // Reset priority filter when showing active
  };
  
  const handleSortByPriority = () => {
    onSortByPriority();
    setSelectedPriority('ALL'); // Reset button state
  };
  
  const handleResetAll = () => {
    setSelectedPriority('ALL');
    setActiveDate(getTodayString());
    onResetFilters();
  };
  
  return (
    <div className="filters-container">
      <div className="filter-group">
        <label className="filter-label">Filter by Priority:</label>
        <div className="filter-buttons">
          <button
            className={`filter-btn ${selectedPriority === 'ALL' ? 'active' : ''}`}
            onClick={() => handlePriorityChange('ALL')}
          >
            All
          </button>
          {Object.values(PRIORITY_LEVELS).map(priority => (
            <button
              key={priority}
              className={`filter-btn ${selectedPriority === priority ? 'active' : ''}`}
              onClick={() => handlePriorityChange(priority)}
            >
              {priority}
            </button>
          ))}
        </div>
      </div>
      
      <div className="filter-group">
        <label className="filter-label">Show Active Tasks for Date:</label>
        <div className="filter-inline">
          <input
            type="date"
            value={activeDate}
            onChange={(e) => setActiveDate(e.target.value)}
            className="date-input"
          />
          <button onClick={handleShowActive} className="btn btn-secondary btn-small">
            Show Active
          </button>
        </div>
      </div>
      
      <div className="filter-group">
        <label className="filter-label">Sort & View:</label>
        <div className="filter-buttons">
          <button onClick={handleSortByPriority} className="btn btn-primary btn-small">
            📊 Sort by Priority (High → Low)
          </button>
          <button onClick={handleResetAll} className="btn btn-secondary btn-small">
            🔄 Show All Tasks
          </button>
        </div>
      </div>
    </div>
  );
};

export default TaskFilters;