package com.university.habittracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a task/habit in the system.
 * Tasks are recurring daily activities that track progress through
 * daily target values and accumulated values.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer dailyTargetValue;

    @Column(nullable = false)
    private Integer accumulatedValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate lastProcessedDate;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompletionHistory> completionHistories = new ArrayList<>();

    // Constructors
    public Task() {
        this.accumulatedValue = 0;
        this.lastProcessedDate = LocalDate.now().minusDays(1);
        this.active = true;
    }

    public Task(String title, String description, Integer dailyTargetValue,
                Priority priority, LocalDate startDate, LocalDate endDate) {
        this();
        this.title = title;
        this.description = description;
        this.dailyTargetValue = dailyTargetValue;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<CompletionHistory> getCompletionHistories() {
        return completionHistories;
    }

    public void setCompletionHistories(List<CompletionHistory> completionHistories) {
        this.completionHistories = completionHistories;
    }

    /**
     * Checks if the task is currently active based on today's date.
     */
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return active && !today.isBefore(startDate) && !today.isAfter(endDate);
    }
}