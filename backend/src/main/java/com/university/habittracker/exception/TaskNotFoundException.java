package com.university.habittracker.exception;

/**
 * Exception thrown when a requested task is not found in the system.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long taskId) {
        super("Task not found with ID: " + taskId);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}