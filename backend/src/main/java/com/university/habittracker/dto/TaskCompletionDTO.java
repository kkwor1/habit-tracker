package com.university.habittracker.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO for marking a task as completed on a specific date.
 */
public class TaskCompletionDTO {

    @NotNull(message = "Task ID is required")
    private Long taskId;

    @NotNull(message = "Completion date is required")
    private LocalDate completionDate;

    // Constructors
    public TaskCompletionDTO() {}

    public TaskCompletionDTO(Long taskId, LocalDate completionDate) {
        this.taskId = taskId;
        this.completionDate = completionDate;
    }

    // Getters and Setters
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}