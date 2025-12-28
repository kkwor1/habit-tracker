package com.university.habittracker.service;

import com.university.habittracker.dto.*;
import com.university.habittracker.entity.Priority;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface defining business operations for task management.
 * Separates business logic from controller layer.
 */
public interface TaskService {

    /**
     * Create a new task
     */
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    /**
     * Get a task by ID
     */
    TaskResponseDTO getTaskById(Long id);

    /**
     * Get all tasks
     */
    List<TaskResponseDTO> getAllTasks();

    /**
     * Get all tasks sorted by priority (HIGH to LOW)
     */
    List<TaskResponseDTO> getAllTasksSortedByPriority();

    /**
     * Get active tasks for a specific date
     */
    List<TaskResponseDTO> getActiveTasksByDate(LocalDate date);

    /**
     * Get tasks filtered by priority
     */
    List<TaskResponseDTO> getTasksByPriority(Priority priority);

    /**
     * Update an existing task
     */
    TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO);

    /**
     * Delete a task
     */
    void deleteTask(Long id);

    /**
     * Mark a task as completed for a specific date
     */
    void completeTask(TaskCompletionDTO completionDTO);

    /**
     * Get completion statistics for a task
     */
    CompletionStatisticsDTO getCompletionStatistics(Long taskId);

    /**
     * Process daily rollover for all tasks
     * This should be called once per day (can be scheduled or manual)
     */
    void processDailyRollover();
}