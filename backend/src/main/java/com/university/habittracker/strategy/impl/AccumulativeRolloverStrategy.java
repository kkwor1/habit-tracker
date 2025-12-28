package com.university.habittracker.strategy.impl;

import com.university.habittracker.entity.Task;
import com.university.habittracker.strategy.RolloverStrategy;
import org.springframework.stereotype.Component;

/**
 * Accumulative rollover strategy implementation.
 *
 * Behavior:
 * - When a day is missed: Add dailyTargetValue to accumulatedValue
 * - When completed: Reset accumulatedValue to dailyTargetValue for next day
 *
 * This strategy ensures that missed days accumulate, creating increasing
 * pressure to complete the task and catch up on missed progress.
 */
@Component
public class AccumulativeRolloverStrategy implements RolloverStrategy {

    @Override
    public void applyRollover(Task task) {
        // Add daily target to accumulated value when day is missed
        int currentAccumulated = task.getAccumulatedValue();
        int dailyTarget = task.getDailyTargetValue();
        task.setAccumulatedValue(currentAccumulated + dailyTarget);
    }

    @Override
    public void applyCompletion(Task task) {
        // Reset to daily target when task is completed
        task.setAccumulatedValue(task.getDailyTargetValue());
    }

    @Override
    public String getStrategyName() {
        return "ACCUMULATIVE";
    }
}