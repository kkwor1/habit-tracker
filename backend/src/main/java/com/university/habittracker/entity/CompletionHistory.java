package com.university.habittracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing the completion record of a task on a specific date.
 * Stores historical data for tracking progress and generating statistics.
 */
@Entity
@Table(name = "completion_history",
        uniqueConstraints = @UniqueConstraint(columnNames = {"task_id", "completion_date"}))
public class CompletionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private LocalDate completionDate;

    @Column(nullable = false)
    private Integer completedValue;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Constructors
    public CompletionHistory() {
        this.timestamp = LocalDateTime.now();
    }

    public CompletionHistory(Task task, LocalDate completionDate, Integer completedValue) {
        this();
        this.task = task;
        this.completionDate = completionDate;
        this.completedValue = completedValue;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getCompletedValue() {
        return completedValue;
    }

    public void setCompletedValue(Integer completedValue) {
        this.completedValue = completedValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}