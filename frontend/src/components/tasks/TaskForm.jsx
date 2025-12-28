import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { PRIORITY_LEVELS } from '../../utils/constants';
import { formatDateForInput } from '../../utils/dateHelpers';
import Button from '../common/Button';

const TaskForm = ({ initialData = null, onSubmit, isEdit = false }) => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        dailyTargetValue: '',
        priority: PRIORITY_LEVELS.MEDIUM,
        startDate: formatDateForInput(new Date()),
        endDate: ''
    });

    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (initialData) {
            setFormData({
                title: initialData.title || '',
                description: initialData.description || '',
                dailyTargetValue: initialData.dailyTargetValue || '',
                priority: initialData.priority || PRIORITY_LEVELS.MEDIUM,
                startDate: formatDateForInput(initialData.startDate) || '',
                endDate: formatDateForInput(initialData.endDate) || ''
            });
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
        // Clear error for this field
        if (errors[name]) {
            setErrors(prev => ({ ...prev, [name]: '' }));
        }
    };

    const validate = () => {
        const newErrors = {};

        if (!formData.title.trim()) {
            newErrors.title = 'Title is required';
        } else if (formData.title.length < 3) {
            newErrors.title = 'Title must be at least 3 characters';
        }

        if (!formData.dailyTargetValue) {
            newErrors.dailyTargetValue = 'Daily target value is required';
        } else if (formData.dailyTargetValue < 1) {
            newErrors.dailyTargetValue = 'Daily target must be at least 1';
        }

        if (!formData.startDate) {
            newErrors.startDate = 'Start date is required';
        }

        if (!formData.endDate) {
            newErrors.endDate = 'End date is required';
        }

        if (formData.startDate && formData.endDate &&
            new Date(formData.endDate) < new Date(formData.startDate)) {
            newErrors.endDate = 'End date must be after start date';
        }

        return newErrors;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setIsSubmitting(true);

        try {
            await onSubmit({
                ...formData,
                dailyTargetValue: parseInt(formData.dailyTargetValue, 10)
            });
            navigate('/');
        } catch (error) {
            setErrors({ submit: error.response?.data?.message || 'Failed to save task' });
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="task-form">
            <div className="form-group">
                <label htmlFor="title" className="form-label">
                    Title <span className="required">*</span>
                </label>
                <input
                    type="text"
                    id="title"
                    name="title"
                    value={formData.title}
                    onChange={handleChange}
                    className={`form-input ${errors.title ? 'error' : ''}`}
                    placeholder="e.g., Read 30 pages daily"
                />
                {errors.title && <span className="error-text">{errors.title}</span>}
            </div>

            <div className="form-group">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea
                    id="description"
                    name="description"
                    value={formData.description}
                    onChange={handleChange}
                    className="form-textarea"
                    rows="4"
                    placeholder="Optional description of your task..."
                />
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label htmlFor="dailyTargetValue" className="form-label">
                        Daily Target Value <span className="required">*</span>
                    </label>
                    <input
                        type="number"
                        id="dailyTargetValue"
                        name="dailyTargetValue"
                        value={formData.dailyTargetValue}
                        onChange={handleChange}
                        className={`form-input ${errors.dailyTargetValue ? 'error' : ''}`}
                        min="1"
                        placeholder="e.g., 30"
                    />
                    {errors.dailyTargetValue && (
                        <span className="error-text">{errors.dailyTargetValue}</span>
                    )}
                </div>

                <div className="form-group">
                    <label htmlFor="priority" className="form-label">
                        Priority <span className="required">*</span>
                    </label>
                    <select
                        id="priority"
                        name="priority"
                        value={formData.priority}
                        onChange={handleChange}
                        className="form-select"
                    >
                        {Object.values(PRIORITY_LEVELS).map(priority => (
                            <option key={priority} value={priority}>
                                {priority}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label htmlFor="startDate" className="form-label">
                        Start Date <span className="required">*</span>
                    </label>
                    <input
                        type="date"
                        id="startDate"
                        name="startDate"
                        value={formData.startDate}
                        onChange={handleChange}
                        className={`form-input ${errors.startDate ? 'error' : ''}`}
                    />
                    {errors.startDate && <span className="error-text">{errors.startDate}</span>}
                </div>

                <div className="form-group">
                    <label htmlFor="endDate" className="form-label">
                        End Date <span className="required">*</span>
                    </label>
                    <input
                        type="date"
                        id="endDate"
                        name="endDate"
                        value={formData.endDate}
                        onChange={handleChange}
                        className={`form-input ${errors.endDate ? 'error' : ''}`}
                    />
                    {errors.endDate && <span className="error-text">{errors.endDate}</span>}
                </div>
            </div>

            {errors.submit && (
                <div className="form-error-banner">
                    {errors.submit}
                </div>
            )}

            <div className="form-actions">
                <Button
                    type="button"
                    variant="secondary"
                    onClick={() => navigate('/')}
                    disabled={isSubmitting}
                >
                    Cancel
                </Button>
                <Button
                    type="submit"
                    variant="primary"
                    disabled={isSubmitting}
                >
                    {isSubmitting ? 'Saving...' : (isEdit ? 'Update Task' : 'Create Task')}
                </Button>
            </div>
        </form>
    );
};

export default TaskForm;