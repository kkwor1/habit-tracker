package com.university.habittracker.controller;

import com.university.habittracker.dto.*;
import com.university.habittracker.entity.Priority;
import com.university.habittracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Task management.
 * Provides endpoints for CRUD operations, filtering, and completion tracking.
 *
 * Base URL: /api/tasks
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Task Management", description = "APIs for managing tasks and habits")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Create a new task
     * POST /api/tasks
     */
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Get a task by ID
     * GET /api/tasks/{id}
     */
    @Operation(summary = "Get task by ID", description = "Retrieves a specific task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @Parameter(description = "Task ID") @PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Get all tasks
     * GET /api/tasks
     */
    @Operation(summary = "Get all tasks", description = "Retrieves all tasks in the system")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get all tasks sorted by priority
     * GET /api/tasks/sorted-by-priority
     */
    @Operation(summary = "Get tasks sorted by priority",
            description = "Retrieves all tasks sorted by priority (HIGH to LOW)")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved and sorted successfully")
    @GetMapping("/sorted-by-priority")
    public ResponseEntity<List<TaskResponseDTO>> getTasksSortedByPriority() {
        List<TaskResponseDTO> tasks = taskService.getAllTasksSortedByPriority();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get active tasks by date
     * GET /api/tasks/active?date=2024-12-26
     */
    @Operation(summary = "Get active tasks for a specific date",
            description = "Retrieves tasks that are active on the specified date")
    @ApiResponse(responseCode = "200", description = "Active tasks retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<List<TaskResponseDTO>> getActiveTasksByDate(
            @Parameter(description = "Date in format yyyy-MM-dd (defaults to today)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate searchDate = (date != null) ? date : LocalDate.now();
        List<TaskResponseDTO> tasks = taskService.getActiveTasksByDate(searchDate);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks filtered by priority
     * GET /api/tasks/by-priority?priority=HIGH
     */
    @Operation(summary = "Get tasks by priority",
            description = "Retrieves tasks filtered by priority level")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/by-priority")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByPriority(
            @Parameter(description = "Priority level (LOW, MEDIUM, HIGH)")
            @RequestParam Priority priority) {
        List<TaskResponseDTO> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Update an existing task
     * PUT /api/tasks/{id}
     */
    @Operation(summary = "Update a task", description = "Updates an existing task with new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @Parameter(description = "Task ID") @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Delete a task
     * DELETE /api/tasks/{id}
     */
    @Operation(summary = "Delete a task", description = "Deletes a task and all its completion history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID") @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark a task as completed for a specific date
     * POST /api/tasks/complete
     */
    @Operation(summary = "Mark task as completed",
            description = "Records a task completion for a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task marked as completed successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid completion data or task already completed for this date")
    })
    @PostMapping("/complete")
    public ResponseEntity<String> completeTask(
            @Valid @RequestBody TaskCompletionDTO completionDTO) {
        taskService.completeTask(completionDTO);
        return ResponseEntity.ok("Task marked as completed successfully");
    }

    /**
     * Get completion statistics for a task
     * GET /api/tasks/{id}/statistics
     */
    @Operation(summary = "Get task completion statistics",
            description = "Retrieves detailed completion statistics for a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CompletionStatisticsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}/statistics")
    public ResponseEntity<CompletionStatisticsDTO> getCompletionStatistics(
            @Parameter(description = "Task ID") @PathVariable Long id) {
        CompletionStatisticsDTO statistics = taskService.getCompletionStatistics(id);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Trigger daily rollover processing
     * POST /api/tasks/process-daily-rollover
     *
     * This endpoint manually triggers the daily rollover logic for all tasks.
     * In a production system, this would typically be scheduled to run automatically.
     */
    @Operation(summary = "Process daily rollover",
            description = "Manually triggers daily rollover processing for all tasks")
    @ApiResponse(responseCode = "200", description = "Daily rollover processed successfully")
    @PostMapping("/process-daily-rollover")
    public ResponseEntity<String> processDailyRollover() {
        taskService.processDailyRollover();
        return ResponseEntity.ok("Daily rollover processed successfully");
    }
}