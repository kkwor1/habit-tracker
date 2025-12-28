package com.university.habittracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Smart Habit Tracker application.
 * This Spring Boot application provides REST APIs for managing tasks
 * with daily rollover logic and completion tracking.
 */
@SpringBootApplication
public class HabitTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitTrackerApplication.class, args);
    }
}