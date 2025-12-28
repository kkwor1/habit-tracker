package com.university.habittracker.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for returning completion statistics for a task.
 */
public class CompletionStatisticsDTO {

    private Long taskId;
    private String taskTitle;
    private Integer totalCompletions;
    private Integer totalPossibleDays;
    private Double completionRate;
    private List<LocalDate> completedDates;
    private LocalDate firstCompletion;
    private LocalDate lastCompletion;

    // Constructors
    public CompletionStatisticsDTO() {}

    public CompletionStatisticsDTO(Long taskId, String taskTitle, Integer totalCompletions,
                                   Integer totalPossibleDays, Double completionRate,
                                   List<LocalDate> completedDates, LocalDate firstCompletion,
                                   LocalDate lastCompletion) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.totalCompletions = totalCompletions;
        this.totalPossibleDays = totalPossibleDays;
        this.completionRate = completionRate;
        this.completedDates = completedDates;
        this.firstCompletion = firstCompletion;
        this.lastCompletion = lastCompletion;
    }

    // Getters and Setters
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Integer getTotalCompletions() {
        return totalCompletions;
    }

    public void setTotalCompletions(Integer totalCompletions) {
        this.totalCompletions = totalCompletions;
    }

    public Integer getTotalPossibleDays() {
        return totalPossibleDays;
    }

    public void setTotalPossibleDays(Integer totalPossibleDays) {
        this.totalPossibleDays = totalPossibleDays;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public List<LocalDate> getCompletedDates() {
        return completedDates;
    }

    public void setCompletedDates(List<LocalDate> completedDates) {
        this.completedDates = completedDates;
    }

    public LocalDate getFirstCompletion() {
        return firstCompletion;
    }

    public void setFirstCompletion(LocalDate firstCompletion) {
        this.firstCompletion = firstCompletion;
    }

    public LocalDate getLastCompletion() {
        return lastCompletion;
    }

    public void setLastCompletion(LocalDate lastCompletion) {
        this.lastCompletion = lastCompletion;
    }
}