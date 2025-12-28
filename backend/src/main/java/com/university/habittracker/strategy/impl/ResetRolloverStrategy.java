package com.university.habittracker.strategy.impl;

import com.university.habittracker.entity.Task;
import com.university.habittracker.strategy.RolloverStrategy;
import org.springframework.stereotype.Component;

/**
 * Reset rollover strategy implementation.
 *
 * Behavior:
 * - When a day is missed: Reset accumulatedValue to dailyTargetValue
 * - When completed: Reset accumulatedValue to dailyTargetValue for next day
 *
 * This strategy treats each day independently, providing a fresh start
 * regardless of previous performance. Useful for habits where consistency
 * matters more than catching up on missed days.
 */
@Component
public class ResetRolloverStrategy implements RolloverStrategy {

    @Override
    public void applyRollover(Task task) {
        // Reset to daily target when day is missed (fresh start each day)
        task.setAccumulatedValue(task.getDailyTargetValue());
    }

    @Override
    public void applyCompletion(Task task) {
        // Reset to daily target when task is completed
        task.setAccumulatedValue(task.getDailyTargetValue());
    }

    @Override
    public String getStrategyName() {
        return "RESET";
    }
}