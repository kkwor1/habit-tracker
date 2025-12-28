package com.university.habittracker.repository;

import com.university.habittracker.entity.CompletionHistory;
import com.university.habittracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CompletionHistory entity.
 * Manages completion records and provides statistics queries.
 */
@Repository
public interface CompletionHistoryRepository extends JpaRepository<CompletionHistory, Long> {

    /**
     * Find completion record for a specific task on a specific date.
     */
    Optional<CompletionHistory> findByTaskAndCompletionDate(Task task, LocalDate completionDate);

    /**
     * Find all completion records for a task, ordered by date descending.
     */
    List<CompletionHistory> findByTaskOrderByCompletionDateDesc(Task task);

    /**
     * Find completion records for a task within a date range.
     */
    @Query("SELECT ch FROM CompletionHistory ch WHERE ch.task = :task " +
            "AND ch.completionDate BETWEEN :startDate AND :endDate " +
            "ORDER BY ch.completionDate DESC")
    List<CompletionHistory> findByTaskAndDateRange(@Param("task") Task task,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    /**
     * Count total completions for a task.
     */
    Long countByTask(Task task);

    /**
     * Check if a task was completed on a specific date.
     */
    boolean existsByTaskAndCompletionDate(Task task, LocalDate completionDate);

    /**
     * Delete all completion records for a task.
     */
    void deleteByTask(Task task);
}