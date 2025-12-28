package com.university.habittracker.service.impl;

import com.university.habittracker.dto.*;
import com.university.habittracker.entity.CompletionHistory;
import com.university.habittracker.entity.Priority;
import com.university.habittracker.entity.Task;
import com.university.habittracker.exception.InvalidDateRangeException;
import com.university.habittracker.exception.TaskNotFoundException;
import com.university.habittracker.repository.CompletionHistoryRepository;
import com.university.habittracker.repository.TaskRepository;
import com.university.habittracker.service.TaskService;
import com.university.habittracker.strategy.RolloverStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of TaskService interface.
 * Contains core business logic for task management and daily rollover processing.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CompletionHistoryRepository completionHistoryRepository;
    private final RolloverStrategy rolloverStrategy;

    public TaskServiceImpl(TaskRepository taskRepository,
                           CompletionHistoryRepository completionHistoryRepository,
                           @Qualifier("accumulativeRolloverStrategy") RolloverStrategy rolloverStrategy) {
        this.taskRepository = taskRepository;
        this.completionHistoryRepository = completionHistoryRepository;
        this.rolloverStrategy = rolloverStrategy;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        validateDateRange(taskRequestDTO.getStartDate(), taskRequestDTO.getEndDate());

        Task task = new Task(
                taskRequestDTO.getTitle(),
                taskRequestDTO.getDescription(),
                taskRequestDTO.getDailyTargetValue(),
                taskRequestDTO.getPriority(),
                taskRequestDTO.getStartDate(),
                taskRequestDTO.getEndDate()
        );

        // Initialize accumulated value to daily target
        task.setAccumulatedValue(taskRequestDTO.getDailyTargetValue());
        task.setActive(true);

        Task savedTask = taskRepository.save(task);
        return convertToResponseDTO(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = findTaskById(id);
        return convertToResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAllTasks().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasksSortedByPriority() {
        LocalDate today = LocalDate.now();
        return taskRepository.findAllByOrderByPriorityDesc(today).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getActiveTasksByDate(LocalDate date) {
        return taskRepository.findActiveTasksByDate(date).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByPriority(Priority priority) {
        LocalDate today = LocalDate.now();
        return taskRepository.findByPriorityOrderByStartDateAsc(priority, today).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task task = findTaskById(id);
        validateDateRange(taskRequestDTO.getStartDate(), taskRequestDTO.getEndDate());

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDailyTargetValue(taskRequestDTO.getDailyTargetValue());
        task.setPriority(taskRequestDTO.getPriority());
        task.setStartDate(taskRequestDTO.getStartDate());
        task.setEndDate(taskRequestDTO.getEndDate());

        Task updatedTask = taskRepository.save(task);
        return convertToResponseDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    @Override
    public void completeTask(TaskCompletionDTO completionDTO) {
        Task task = findTaskById(completionDTO.getTaskId());
        LocalDate completionDate = completionDTO.getCompletionDate();

        // Validate completion date is within task's date range
        if (completionDate.isBefore(task.getStartDate()) ||
                completionDate.isAfter(task.getEndDate())) {
            throw new InvalidDateRangeException(
                    "Completion date must be between task start date and end date"
            );
        }

        // Check if already completed on this date
        if (completionHistoryRepository.existsByTaskAndCompletionDate(task, completionDate)) {
            throw new IllegalStateException(
                    "Task already marked as completed for date: " + completionDate
            );
        }

        // Apply completion strategy
        rolloverStrategy.applyCompletion(task);

        // Mark task inactive when completed (user-requested behavior)
        task.setActive(false);

        // Create completion history record
        CompletionHistory completion = new CompletionHistory(
                task,
                completionDate,
                task.getAccumulatedValue()
        );

        completionHistoryRepository.save(completion);

        // Update last processed date if completing today
        if (completionDate.equals(LocalDate.now())) {
            task.setLastProcessedDate(LocalDate.now());
        }

        taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public CompletionStatisticsDTO getCompletionStatistics(Long taskId) {
        Task task = findTaskById(taskId);

        List<CompletionHistory> completions =
                completionHistoryRepository.findByTaskOrderByCompletionDateDesc(task);

        // Calculate total possible days (from start date to today or end date, whichever is earlier)
        LocalDate today = LocalDate.now();
        LocalDate endCalculation = task.getEndDate().isBefore(today) ? task.getEndDate() : today;
        LocalDate startCalculation = task.getStartDate().isAfter(today) ? today : task.getStartDate();

        long totalPossibleDays = ChronoUnit.DAYS.between(startCalculation, endCalculation) + 1;
        if (totalPossibleDays < 0) totalPossibleDays = 0;

        int totalCompletions = completions.size();
        double completionRate = totalPossibleDays > 0
                ? (double) totalCompletions / totalPossibleDays * 100.0
                : 0.0;

        List<LocalDate> completedDates = completions.stream()
                .map(CompletionHistory::getCompletionDate)
                .collect(Collectors.toList());

        LocalDate firstCompletion = completions.isEmpty()
                ? null
                : completions.get(completions.size() - 1).getCompletionDate();
        LocalDate lastCompletion = completions.isEmpty()
                ? null
                : completions.get(0).getCompletionDate();

        return new CompletionStatisticsDTO(
                task.getId(),
                task.getTitle(),
                totalCompletions,
                (int) totalPossibleDays,
                Math.round(completionRate * 100.0) / 100.0,
                completedDates,
                firstCompletion,
                lastCompletion
        );
    }

    @Override
    public void processDailyRollover() {
        LocalDate today = LocalDate.now();
        List<Task> tasksToProcess = taskRepository.findTasksNeedingDailyProcessing(today);

        for (Task task : tasksToProcess) {
            LocalDate lastProcessed = task.getLastProcessedDate();

            // Calculate how many days passed since last processing
            long daysSinceLastProcessed = ChronoUnit.DAYS.between(lastProcessed, today);

            // Process each day individually
            for (long i = 1; i <= daysSinceLastProcessed; i++) {
                LocalDate processDate = lastProcessed.plusDays(i);

                // Skip if before task start date
                if (processDate.isBefore(task.getStartDate())) {
                    continue;
                }

                // Check if task was completed on this date
                boolean wasCompleted = completionHistoryRepository
                        .existsByTaskAndCompletionDate(task, processDate);

                if (!wasCompleted) {
                    // Not completed: add daily target to accumulated value
                    int currentAccumulated = task.getAccumulatedValue();
                    int dailyTarget = task.getDailyTargetValue();
                    task.setAccumulatedValue(currentAccumulated + dailyTarget);

                    System.out.println("Task " + task.getId() + " - Date: " + processDate +
                            " - Not completed. Accumulated: " + currentAccumulated +
                            " -> " + task.getAccumulatedValue());
                } else {
                    // Completed: reset to daily target for next day
                    task.setAccumulatedValue(task.getDailyTargetValue());

                    System.out.println("Task " + task.getId() + " - Date: " + processDate +
                            " - Completed. Reset to: " + task.getDailyTargetValue());
                }
            }

            // Update last processed date to today
            task.setLastProcessedDate(today);
            taskRepository.save(task);
        }
    }

    // Helper methods

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidDateRangeException(
                    "End date must be after or equal to start date"
            );
        }
    }

    private TaskResponseDTO convertToResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDailyTargetValue(),
                task.getAccumulatedValue(),
                task.getPriority(),
                task.getStartDate(),
                task.getEndDate(),
                task.getLastProcessedDate(),
                task.isActive()
        );
    }
}