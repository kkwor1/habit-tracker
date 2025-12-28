# Smart Habit Tracker - React Frontend

Production-ready React SPA for the Smart Habit Tracker application.

## Features

- ✅ Complete task management (CRUD operations)
- 📊 Real-time statistics and completion tracking
- 🎯 Priority-based filtering and sorting
- 📅 Date-based filtering for active tasks
- 📱 Fully responsive design
- ⚡ Fast and optimized with Vite
- 🎨 Clean, modern UI with CSS Grid & Flexbox

## Tech Stack

- **React 18** - UI library
- **Vite** - Build tool and dev server
- **React Router 6** - Client-side routing
- **Axios** - HTTP client
- **CSS3** - Styling (no frameworks, pure CSS)

## Prerequisites

- Node.js 16+ and npm
- Spring Boot backend running on `http://localhost:8080`

## Installation
```bash
# Install dependencies
npm install

# Start development server
npm run dev
```

The application will be available at `http://localhost:5173`

## Project Structure
````
src/
├── api/              # Axios configuration and API services
├── components/       # Reusable React components
│   ├── common/      # Generic UI components
│   ├── layout/      # Layout components
│   ├── tasks/       # Task-specific components
│   └── statistics/  # Statistics components
├── hooks/           # Custom React hooks
├── pages/           # Page components (routes)
├── styles/          # CSS files
├── utils/           # Utility functions and constants
├── App.jsx          # Root component
├── main.jsx         # Application entry point
└── router.jsx       # Route configuration
````

# Smart Habit Tracker - Backend API

Production-ready Spring Boot REST API for managing tasks and habits with daily rollover logic.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technical Stack](#technical-stack)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Business Logic](#business-logic)
- [Design Patterns](#design-patterns)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)

---

## 🎯 Overview

Smart Habit Tracker is a backend system designed to help users manage daily habits and tasks with intelligent rollover logic. The system automatically tracks missed days and accumulates pending work, ensuring users stay accountable to their goals.

### Key Concepts

- **Tasks**: Recurring activities with daily targets (e.g., read 30 pages per day)
- **Daily Rollover**: Automatic accumulation of missed work to the next day
- **Completion Tracking**: Historical record of task completions with statistics
- **Priority Management**: Organize tasks by importance (LOW, MEDIUM, HIGH)

---

## ✨ Features

### Core Functionality

- ✅ **CRUD Operations**: Create, read, update, and delete tasks
- 📊 **Daily Rollover Logic**: Automatically accumulate missed daily targets
- ✓ **Completion Tracking**: Mark tasks as completed with date stamps
- 📈 **Statistics**: Calculate completion rates and view historical data
- 🎯 **Priority Filtering**: Filter and sort tasks by priority levels
- 📅 **Date-Based Filtering**: View active tasks for specific dates
- 🔄 **Automatic Processing**: Daily rollover updates all pending tasks

### Business Rules

1. **Task Creation**: 
   - Each task has a daily target value (e.g., pages to read, exercises to do)
   - Tasks run from startDate to endDate (inclusive)
   - Initial accumulated value equals daily target value

2. **Daily Rollover**:
   - If task NOT completed: `accumulatedValue += dailyTargetValue`
   - If task completed: `accumulatedValue = dailyTargetValue` (reset for next day)
   - Processes all days between last processed date and today

3. **Completion**:
   - Tasks can be marked complete for any date within their active period
   - Once completed for a date, task won't appear in lists for that date
   - Completion resets accumulated value to daily target

4. **Task Visibility**:
   - Completed tasks don't show in today's view
   - Tasks reappear the next day (if not completed again)
   - Inactive tasks (past end date) don't show in active filters

---

## 🛠 Technical Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 17 |
| **Framework** | Spring Boot | 3.2.0 |
| **Web** | Spring Web (REST) | - |
| **Data Access** | Spring Data JPA | - |
| **ORM** | Hibernate | - |
| **Database (Dev)** | H2 (in-memory) | - |
| **Database (Prod)** | PostgreSQL / MySQL | - |
| **Build Tool** | Maven | 3.9+ |
| **API Docs** | SpringDoc OpenAPI | 2.3.0 |
| **Validation** | Hibernate Validator | - |

---

## 🏗 Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│         Controller Layer            │  ← REST endpoints
│    (TaskController.java)            │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│          Service Layer              │  ← Business logic
│   (TaskService, TaskServiceImpl)    │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│        Repository Layer             │  ← Data access
│  (TaskRepository, HistoryRepo)      │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│         Database (H2/SQL)           │  ← Persistence
└─────────────────────────────────────┘
```

### Package Structure

```
com.university.habittracker/
├── controller/          # REST API endpoints
├── service/            # Business logic layer
│   └── impl/          # Service implementations
├── repository/         # Data access layer
├── entity/            # JPA entities
├── dto/               # Data Transfer Objects
├── strategy/          # Strategy pattern implementations
│   └── impl/         # Rollover strategies
├── exception/         # Custom exceptions
└── config/           # Configuration classes
```

---

## 📦 Prerequisites

- **Java JDK**: Version 17 or higher
- **Maven**: Version 3.9 or higher
- **IDE** (Optional): IntelliJ IDEA, Eclipse, or VS Code
- **Git**: For version control
- **Postman/Curl**: For API testing (optional)

### Verify Installation

```bash
# Check Java version
java -version
# Should output: java version "17.x.x"

# Check Maven version
mvn -version
# Should output: Apache Maven 3.9.x
```

---

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd smart-habit-tracker
```

### 2. Project Structure

Ensure your project has this structure:

```
smart-habit-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/university/habittracker/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml
└── README.md
```

### 3. Configure Database (Optional)

The application uses H2 in-memory database by default. To use PostgreSQL/MySQL:

**Edit `src/main/resources/application.yml`:**

```yaml
# For PostgreSQL
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/habitdb
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect

# For MySQL
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/habitdb
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
```

**Add dependency to `pom.xml`:**

```xml
<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- OR MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 🏃 Running the Application

### Option 1: Using Maven

```bash
# Clean and install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

### Option 2: Using JAR

```bash
# Build JAR file
mvn clean package

# Run the JAR
java -jar target/habit-tracker-1.0.0.jar
```

### Option 3: Using IDE

1. Open project in IntelliJ IDEA / Eclipse
2. Navigate to `HabitTrackerApplication.java`
3. Right-click → Run 'HabitTrackerApplication'

### Verify Application Started

```bash
# Check console output for:
# "Started HabitTrackerApplication in X.XXX seconds"

# Test health endpoint
curl http://localhost:8080/actuator/health
```

---

## 📚 API Documentation

### Access Swagger UI

Once the application is running:

🌐 **Swagger UI**: http://localhost:8080/swagger-ui.html  
📄 **OpenAPI JSON**: http://localhost:8080/api-docs

### API Endpoints

#### Task Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| **POST** | `/api/tasks` | Create new task | TaskRequestDTO |
| **GET** | `/api/tasks` | Get all tasks | - |
| **GET** | `/api/tasks/{id}` | Get task by ID | - |
| **PUT** | `/api/tasks/{id}` | Update task | TaskRequestDTO |
| **DELETE** | `/api/tasks/{id}` | Delete task | - |

#### Filtering & Sorting

| Method | Endpoint | Description | Query Params |
|--------|----------|-------------|--------------|
| **GET** | `/api/tasks/sorted-by-priority` | Get tasks sorted by priority | - |
| **GET** | `/api/tasks/active` | Get active tasks | `?date=yyyy-MM-dd` |
| **GET** | `/api/tasks/by-priority` | Filter by priority | `?priority=HIGH` |

#### Completion & Statistics

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| **POST** | `/api/tasks/complete` | Mark task completed | TaskCompletionDTO |
| **GET** | `/api/tasks/{id}/statistics` | Get completion stats | - |
| **POST** | `/api/tasks/process-daily-rollover` | Trigger rollover | - |

### Example Requests

#### 1. Create a Task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Read technical books",
    "description": "Read 30 pages of software engineering books daily",
    "dailyTargetValue": 30,
    "priority": "HIGH",
    "startDate": "2024-12-28",
    "endDate": "2025-01-28"
  }'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Read technical books",
  "description": "Read 30 pages of software engineering books daily",
  "dailyTargetValue": 30,
  "accumulatedValue": 30,
  "priority": "HIGH",
  "startDate": "2024-12-28",
  "endDate": "2025-01-28",
  "lastProcessedDate": "2024-12-27",
  "active": true
}
```

#### 2. Get All Tasks

```bash
curl http://localhost:8080/api/tasks
```

#### 3. Get Active Tasks for Today

```bash
curl http://localhost:8080/api/tasks/active
```

#### 4. Get Active Tasks for Specific Date

```bash
curl http://localhost:8080/api/tasks/active?date=2025-01-01
```

#### 5. Filter by Priority

```bash
curl http://localhost:8080/api/tasks/by-priority?priority=HIGH
```

#### 6. Mark Task as Completed

```bash
curl -X POST http://localhost:8080/api/tasks/complete \
  -H "Content-Type: application/json" \
  -d '{
    "taskId": 1,
    "completionDate": "2024-12-28"
  }'
```

**Response (200 OK):**
```json
"Task marked as completed successfully"
```

#### 7. Get Task Statistics

```bash
curl http://localhost:8080/api/tasks/1/statistics
```

**Response:**
```json
{
  "taskId": 1,
  "taskTitle": "Read technical books",
  "totalCompletions": 15,
  "totalPossibleDays": 20,
  "completionRate": 75.0,
  "completedDates": ["2024-12-28", "2024-12-27", "2024-12-26"],
  "firstCompletion": "2024-12-10",
  "lastCompletion": "2024-12-28"
}
```

#### 8. Process Daily Rollover

```bash
curl -X POST http://localhost:8080/api/tasks/process-daily-rollover
```

**Response:**
```json
"Daily rollover processed successfully"
```

---

## 🗄 Database Schema

### Tables

#### 1. **tasks**

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier |
| `title` | VARCHAR(200) | NOT NULL | Task title |
| `description` | VARCHAR(1000) | NULL | Task description |
| `daily_target_value` | INTEGER | NOT NULL | Daily target amount |
| `accumulated_value` | INTEGER | NOT NULL | Current accumulated value |
| `priority` | VARCHAR(20) | NOT NULL | LOW, MEDIUM, HIGH |
| `start_date` | DATE | NOT NULL | Task start date |
| `end_date` | DATE | NOT NULL | Task end date |
| `last_processed_date` | DATE | NOT NULL | Last rollover date |

#### 2. **completion_history**

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier |
| `task_id` | BIGINT | FOREIGN KEY → tasks(id) | Associated task |
| `completion_date` | DATE | NOT NULL | Date completed |
| `completed_value` | INTEGER | NOT NULL | Value at completion |
| `timestamp` | TIMESTAMP | NOT NULL | Completion timestamp |

**Unique Constraint**: `(task_id, completion_date)` - Prevents duplicate completions

### Relationships

- **tasks** 1 ←→ N **completion_history**
  - One task can have many completion records
  - Cascade: ALL, Orphan Removal: TRUE

### H2 Console Access

When running with H2 database:

🌐 **H2 Console**: http://localhost:8080/h2-console

**Connection Settings:**
- **JDBC URL**: `jdbc:h2:mem:habitdb`
- **Username**: `sa`
- **Password**: (leave empty)

---

## 💼 Business Logic

### Daily Rollover Algorithm

The core algorithm that makes this system unique:

```java
FOR each task in needsProcessing:
    daysSinceLastProcessed = today - task.lastProcessedDate
    
    FOR each day in (lastProcessedDate + 1) to today:
        IF task was completed on this day:
            accumulatedValue = dailyTargetValue  // Reset
        ELSE:
            accumulatedValue += dailyTargetValue // Accumulate
    
    task.lastProcessedDate = today
    SAVE task
```

### Example Scenario

**Task Configuration:**
- Daily Target: 30 pages
- Start Date: 2024-12-25
- End Date: 2025-01-25

**Timeline:**

| Date | Action | Accumulated Value | Calculation |
|------|--------|-------------------|-------------|
| Dec 25 | Created | 30 | Initial value |
| Dec 26 | Not completed | 60 | 30 + 30 |
| Dec 27 | Completed ✓ | 30 | Reset to daily target |
| Dec 28 | Not completed | 60 | 30 + 30 |
| Dec 29 | Not completed | 90 | 60 + 30 |
| Dec 30 | Completed ✓ | 30 | Reset to daily target |

### Strategy Pattern Implementation

Two rollover strategies are implemented:

#### 1. **AccumulativeRolloverStrategy** (Default)
```java
applyRollover(): accumulatedValue += dailyTargetValue
applyCompletion(): accumulatedValue = dailyTargetValue
```

#### 2. **ResetRolloverStrategy**
```java
applyRollover(): accumulatedValue = dailyTargetValue
applyCompletion(): accumulatedValue = dailyTargetValue
```

Switch strategies by changing the `@Primary` annotation in the strategy implementation classes.

---

## 🎨 Design Patterns

### 1. **Strategy Pattern**
- **Location**: `strategy/` package
- **Purpose**: Encapsulate rollover algorithms
- **Benefit**: Easy to add new rollover behaviors without modifying existing code

### 2. **Repository Pattern**
- **Location**: `repository/` package
- **Purpose**: Abstract data access layer
- **Benefit**: Database-agnostic business logic

### 3. **DTO Pattern**
- **Location**: `dto/` package
- **Purpose**: Separate internal entities from API contracts
- **Benefit**: Protect entity structure, enable validation

### 4. **Layered Architecture**
- **Layers**: Controller → Service → Repository
- **Purpose**: Separation of concerns
- **Benefit**: Maintainable, testable, scalable code

### 5. **Dependency Injection**
- **Framework**: Spring IoC Container
- **Style**: Constructor injection
- **Benefit**: Loose coupling, easy testing

### 6. **AOP (Aspect-Oriented Programming)**
- **Location**: `GlobalExceptionHandler`
- **Purpose**: Cross-cutting concerns (error handling)
- **Benefit**: Centralized exception handling

---

## 🧪 Testing

### Manual Testing with Swagger

1. Start application
2. Open http://localhost:8080/swagger-ui.html
3. Test each endpoint using the interactive UI

### Manual Testing with Curl

```bash
# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Task","dailyTargetValue":10,"priority":"HIGH","startDate":"2024-12-28","endDate":"2025-01-28"}'

# Get all tasks
curl http://localhost:8080/api/tasks

# Complete a task
curl -X POST http://localhost:8080/api/tasks/complete \
  -H "Content-Type: application/json" \
  -d '{"taskId":1,"completionDate":"2024-12-28"}'

# Process rollover
curl -X POST http://localhost:8080/api/tasks/process-daily-rollover

# Get statistics
curl http://localhost:8080/api/tasks/1/statistics
```

### Test Scenarios

#### Scenario 1: Daily Rollover
1. Create task with dailyTargetValue = 10
2. Note accumulatedValue (should be 10)
3. Call `/process-daily-rollover`
4. Check accumulatedValue (should be 20)
5. Call `/process-daily-rollover` again
6. Check accumulatedValue (should be 30)

#### Scenario 2: Completion Resets Value
1. Create task with dailyTargetValue = 10
2. Call `/process-daily-rollover` 3 times
3. accumulatedValue should be 40
4. Complete task for today
5. accumulatedValue should reset to 10

#### Scenario 3: Priority Filtering
1. Create 3 tasks: HIGH, MEDIUM, LOW
2. Call `/api/tasks/by-priority?priority=MEDIUM`
3. Should return only MEDIUM priority task

#### Scenario 4: Date Filtering
1. Create task: start=yesterday, end=tomorrow
2. Create task: start=+2days, end=+10days
3. Call `/api/tasks/active` (defaults to today)
4. Should return only first task
5. Call `/api/tasks/active?date=+3days`
6. Should return only second task

---

## 📁 Project Structure

```
smart-habit-tracker/
├── src/
│   ├── main/
│   │   ├── java/com/university/habittracker/
│   │   │   ├── HabitTrackerApplication.java       # Main entry point
│   │   │   ├── controller/
│   │   │   │   └── TaskController.java            # REST endpoints
│   │   │   ├── service/
│   │   │   │   ├── TaskService.java               # Service interface
│   │   │   │   └── impl/
│   │   │   │       └── TaskServiceImpl.java       # Business logic
│   │   │   ├── repository/
│   │   │   │   ├── TaskRepository.java            # Task data access
│   │   │   │   └── CompletionHistoryRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── Task.java                      # Task entity
│   │   │   │   ├── CompletionHistory.java         # Completion entity
│   │   │   │   └── Priority.java                  # Priority enum
│   │   │   ├── dto/
│   │   │   │   ├── TaskRequestDTO.java            # Create/Update DTO
│   │   │   │   ├── TaskResponseDTO.java           # Response DTO
│   │   │   │   ├── TaskCompletionDTO.java         # Completion DTO
│   │   │   │   └── CompletionStatisticsDTO.java   # Statistics DTO
│   │   │   ├── strategy/
│   │   │   │   ├── RolloverStrategy.java          # Strategy interface
│   │   │   │   └── impl/
│   │   │   │       ├── AccumulativeRolloverStrategy.java
│   │   │   │       └── ResetRolloverStrategy.java
│   │   │   ├── exception/
│   │   │   │   ├── TaskNotFoundException.java
│   │   │   │   ├── InvalidDateRangeException.java
│   │   │   │   └── GlobalExceptionHandler.java    # Error handling
│   │   │   └── config/
│   │   │       └── SwaggerConfig.java             # API docs config
│   │   └── resources/
│   │       └── application.yml                    # Configuration
│   └── test/
│       └── java/...                               # Unit tests
├── target/                                        # Build output
├── pom.xml                                        # Maven config
└── README.md                                      # This file
```

---

## ⚙️ Configuration

### Application Properties

**File**: `src/main/resources/application.yml`

```yaml
# Server Configuration
server:
  port: 8080
  servlet:
    context-path: /

# Spring Application
spring:
  application:
    name: smart-habit-tracker
  
  # Database Configuration
  datasource:
    url: jdbc:h2:mem:habitdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  
  # H2 Console
  h2:
    console:
      enabled: true
      path: /h2-console
  
  # JPA/Hibernate
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop  # Change to 'update' for production
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true

# API Documentation
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

### Environment-Specific Configurations

Create separate profile files:

**application-dev.yml** (Development)
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

**application-prod.yml** (Production)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://prod-db:5432/habitdb
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
```

**Run with profile:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 🔧 Troubleshooting

### Common Issues

#### 1. Port 8080 Already in Use

**Error**: `Port 8080 is already in use`

**Solution 1**: Change port in `application.yml`
```yaml
server:
  port: 8081
```

**Solution 2**: Kill process using port 8080
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

#### 2. Database Connection Failed

**Error**: `Unable to create initial connections of pool`

**Solution**:
- Check database is running
- Verify credentials in `application.yml`
- Ensure database exists
- Check firewall settings

#### 3. Maven Build Fails

**Error**: `Failed to execute goal org.apache.maven.plugins`

**Solution**:
```bash
# Clean Maven cache
mvn clean

# Update dependencies
mvn dependency:purge-local-repository

# Rebuild
mvn clean install -U
```

#### 4. Java Version Mismatch

**Error**: `class file has wrong version`

**Solution**:
- Verify Java 17 is installed: `java -version`
- Update `JAVA_HOME` environment variable
- In `pom.xml`, verify:
```xml
<properties>
    <java.version>17</java.version>
</properties>
```

#### 5. H2 Console Not Accessible

**Solution**:
- Verify `spring.h2.console.enabled=true`
- Check URL: http://localhost:8080/h2-console
- Use JDBC URL: `jdbc:h2:mem:habitdb`

---

## 📝 Additional Resources

### Spring Boot Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)

### API Documentation Tools
- [Swagger/OpenAPI](https://swagger.io/docs/)
- [SpringDoc](https://springdoc.org/)

### Design Patterns
- [Refactoring Guru - Design Patterns](https://refactoring.guru/design-patterns)
- [Baeldung - Spring Tutorials](https://www.baeldung.com/)


---

## 👥 Contributors

- **Developer**: Kubatov Kairat
- **Project Type**: Final Project
- **Academic Year**: 2024-2025
- **Course**: Object-Oriented Programming / Web and Internet Technologies

---

## 🎓 Grading Criteria Coverage

### OOP Principles ✅
- ✓ Encapsulation (private fields, getters/setters)
- ✓ Inheritance (entity hierarchy)
- ✓ Polymorphism (Strategy pattern)
- ✓ Abstraction (interfaces for service and strategy layers)

### Design Patterns ✅
- ✓ Strategy Pattern (rollover logic)
- ✓ Repository Pattern (data access)
- ✓ DTO Pattern (data transfer)
- ✓ Dependency Injection (Spring IoC)
- ✓ Layered Architecture (Controller-Service-Repository)

### REST API Quality ✅
- ✓ RESTful endpoints with proper HTTP methods
- ✓ Clear URL structure (`/api/tasks`)
- ✓ Appropriate status codes (200, 201, 204, 400, 404, 500)
- ✓ Comprehensive Swagger documentation
- ✓ Input validation with `@Valid`
- ✓ Proper error handling

### Business Logic ✅
- ✓ Daily rollover implementation
- ✓ Completion tracking with history
- ✓ Statistics calculation
- ✓ Date range validation
- ✓ Priority-based filtering and sorting
- ✓ Complex query operations

---

## 🚀 Quick Start Commands

```bash
# Clone and setup
git clone <repo-url>
cd smart-habit-tracker
mvn clean install

# Run application
mvn spring-boot:run

# Access endpoints
# API: http://localhost:8080/api/tasks
# Swagger: http://localhost:8080/swagger-ui.html
# H2 Console: http://localhost:8080/h2-console

# Test endpoints
curl http://localhost:8080/api/tasks
curl -X POST http://localhost:8080/api/tasks/process-daily-rollover
```

---

## 📞 Support

For questions or issues:
1. Check [Troubleshooting](#troubleshooting) section
2. Review [API Documentation](#api-documentation)
3. Contact project supervisor
4. Check application logs in console

---

**Last Updated**: December 28, 2024  
**Version**: 1.0.0  
**Status**: Production Ready ✅