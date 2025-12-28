import React, { useState } from 'react';
import { useTasks } from '../hooks/useTasks';
import TaskList from '../components/tasks/TaskList';
import TaskFilters from '../components/tasks/TaskFilters';
import Loading from '../components/common/Loading';
import ErrorMessage from '../components/common/ErrorMessage';
import Button from '../components/common/Button';

const HomePage = () => {
  const { 
    tasks, 
    loading, 
    error, 
    refetch, 
    deleteTask, 
    completeTask,
    filterByPriority,
    getActiveTasks,
    sortByPriority
  , processDailyRollover } = useTasks();
  
  const [processing, setProcessing] = useState(false);
  
  const handleFilterChange = async (priority) => {
    await filterByPriority(priority);
  };
  
  const handleShowActive = async (date) => {
    await getActiveTasks(date);
  };
  
  const handleSortByPriority = async () => {
    await sortByPriority();
  };
  
  const handleResetFilters = async () => {
    await refetch();
  };
  
  const handleProcessRollover = async () => {
    if (window.confirm('Process daily rollover for all tasks? This will increase accumulated values for incomplete tasks.')) {
      setProcessing(true);
      try {
        const res = await processDailyRollover();
        if (res.success) {
          alert('Daily rollover processed successfully! Accumulated values updated.');
        } else {
          alert('Failed to process rollover: ' + (res.error || 'Unknown'));
        }
      } catch (err) {
        const errorMsg = err.response?.data?.message || err.message || 'Unknown error';
        alert('Failed to process rollover: ' + errorMsg);
        console.error('Rollover error:', err);
      } finally {
        setProcessing(false);
      }
    }
  };
  
  if (loading) return <Loading message="Loading tasks..." />;
  if (error) return <ErrorMessage message={error} onRetry={refetch} />;
  
  return (
    <div className="page-container">
      <div className="page-header">
        <div>
          <h1 className="page-title">My Tasks</h1>
          <p className="page-subtitle">
            Manage your daily habits and track your progress
          </p>
        </div>
        <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
          <Button
            onClick={handleSortByPriority}
            variant="primary"
          >
            📊 Sort by Priority
          </Button>
          <Button
            onClick={handleProcessRollover}
            variant="secondary"
            disabled={processing}
          >
            {processing ? 'Processing...' : '🔄 Process Daily Rollover'}
          </Button>
        </div>
      </div>
      
      <TaskFilters
        onFilterChange={handleFilterChange}
        onShowActive={handleShowActive}
        onSortByPriority={handleSortByPriority}
        onResetFilters={handleResetFilters}
      />
      
      <TaskList
        tasks={tasks}
        onDelete={deleteTask}
        onComplete={completeTask}
      />
    </div>
  );
};

export default HomePage;