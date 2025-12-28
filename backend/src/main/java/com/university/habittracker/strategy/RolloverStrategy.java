package com.university.habittracker.strategy;

import com.university.habittracker.entity.Task;

/**
 * Strategy interface for handling daily rollover logic.
 * This pattern allows different rollover behaviors to be implemented
 * and swapped without changing the core business logic.
 *
 * Design Pattern: Strategy Pattern
 * Purpose: Encapsulate the algorithm for daily value rollover
 */
public interface RolloverStrategy {

    /**
     * Apply the rollover logic to a task.
     * This method is called when a day passes without the task being completed.
     *
     * @param task The task to apply rollover logic to
     */
    void applyRollover(Task task);

    /**
     * Apply completion logic to a task.
     * This method is called when a task is marked as completed.
     *
     * @param task The task to apply completion logic to
     */
    void applyCompletion(Task task);

    /**
     * Get the strategy name for identification purposes.
     */
    String getStrategyName();
}