package com.university.habittracker.dto;

import com.university.habittracker.entity.Priority;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO for creating and updating tasks.
 * Contains validation rules to ensure data integrity.
 */
public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Daily target value is required")
    @Min(value = 1, message = "Daily target value must be at least 1")
    private Integer dailyTargetValue;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    // Constructors
    public TaskRequestDTO() {}

    public TaskRequestDTO(String title, String description, Integer dailyTargetValue,
                          Priority priority, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.dailyTargetValue = dailyTargetValue;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDailyTargetValue() {
        return dailyTargetValue;
    }

    public void setDailyTargetValue(Integer dailyTargetValue) {
        this.dailyTargetValue = dailyTargetValue;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}