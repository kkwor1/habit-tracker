package com.university.habittracker.repository;

import com.university.habittracker.entity.Priority;
import com.university.habittracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Task entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks without filtering completed ones.
     * Used when user wants to see ALL tasks.
     */
    @Query("SELECT t FROM Task t ORDER BY t.id DESC")
    List<Task> findAllTasks();

    /**
     * Find all tasks sorted by priority in descending order (HIGH to LOW).
     * Excludes tasks completed today.
     */
    @Query("SELECT t FROM Task t WHERE t.active = true AND NOT EXISTS " +
            "(SELECT ch FROM CompletionHistory ch WHERE ch.task = t AND ch.completionDate = :today) " +
            "ORDER BY (CASE WHEN t.priority = com.university.habittracker.entity.Priority.HIGH THEN 3 " +
            "WHEN t.priority = com.university.habittracker.entity.Priority.MEDIUM THEN 2 " +
            "ELSE 1 END) DESC, t.id DESC")
    List<Task> findAllByOrderByPriorityDesc(@Param("today") LocalDate today);

    /**
     * Find active tasks for a specific date, sorted by priority.
     * A task is active if the date falls between startDate and endDate (inclusive).
     * Excludes tasks completed on the specified date.
     */
    @Query("SELECT t FROM Task t WHERE t.active = true AND :date >= t.startDate AND :date <= t.endDate " +
            "AND NOT EXISTS (SELECT ch FROM CompletionHistory ch WHERE ch.task = t AND ch.completionDate = :date) " +
            "ORDER BY (CASE WHEN t.priority = com.university.habittracker.entity.Priority.HIGH THEN 3 " +
            "WHEN t.priority = com.university.habittracker.entity.Priority.MEDIUM THEN 2 " +
            "ELSE 1 END) DESC")
    List<Task> findActiveTasksByDate(@Param("date") LocalDate date);

    /**
     * Find tasks by priority, sorted by start date.
     * Excludes tasks completed today.
     */
    @Query("SELECT t FROM Task t WHERE t.active = true AND t.priority = :priority " +
            "AND NOT EXISTS (SELECT ch FROM CompletionHistory ch WHERE ch.task = t AND ch.completionDate = :today) " +
            "ORDER BY t.startDate ASC")
    List<Task> findByPriorityOrderByStartDateAsc(@Param("priority") Priority priority,
                                                 @Param("today") LocalDate today);

    /**
     * Find all tasks that need daily processing (lastProcessedDate is before today).
     */
        @Query("SELECT t FROM Task t WHERE t.active = true AND t.lastProcessedDate < :today AND t.endDate >= :today")
        List<Task> findTasksNeedingDailyProcessing(@Param("today") LocalDate today);

    /**
     * Find tasks ending between two dates.
     */
    @Query("SELECT t FROM Task t WHERE t.endDate BETWEEN :startDate AND :endDate")
    List<Task> findTasksEndingBetween(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}