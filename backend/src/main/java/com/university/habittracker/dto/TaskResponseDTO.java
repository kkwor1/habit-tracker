package com.university.habittracker.dto;

import com.university.habittracker.entity.Priority;
import java.time.LocalDate;

/**
 * DTO for returning task data to clients.
 * Protects internal entity structure from direct exposure.
 */
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Integer dailyTargetValue;
    private Integer accumulatedValue;
    private Priority priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastProcessedDate;
    private boolean active;

    // Constructors
    public TaskResponseDTO() {}

    public TaskResponseDTO(Long id, String title, String description,
                           Integer dailyTargetValue, Integer accumulatedValue,
                           Priority priority, LocalDate startDate, LocalDate endDate,
                           LocalDate lastProcessedDate, boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dailyTargetValue = dailyTargetValue;
        this.accumulatedValue = accumulatedValue;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastProcessedDate = lastProcessedDate;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getAccumulatedValue() {
        return accumulatedValue;
    }

    public void setAccumulatedValue(Integer accumulatedValue) {
        this.accumulatedValue = accumulatedValue;
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

    public LocalDate getLastProcessedDate() {
        return lastProcessedDate;
    }

    public void setLastProcessedDate(LocalDate lastProcessedDate) {
        this.lastProcessedDate = lastProcessedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}