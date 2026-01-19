# Task Management API 

A secure, role-based **Task Management REST API** built with **Spring Boot**.  

This project manages **Employees**, **Tasks**, and **Users**, with:

- One-to-many relationship between **Employee** and **Task**
- **JWT-based authentication & authorization**
- Role-based access: `ADMIN` and `USER`
- **Swagger/OpenAPI** documentation
- Layered architecture with DTOs, Services, Repositories, Filters, and Exception Handling

## ✨ Features  (Scroll down to find out How to use this API)

- ✅ JWT authentication (`/signin/add`, `/signin/token`)
- ✅ Role-based authorization (`ADMIN`, `USER`)
  - `USER`: can perform **GET** and **PUT**
  - `ADMIN`: can perform **GET**, **PUT**, **POST**, **DELETE**
- ✅ Manage employees (CRUD + pagination)
- ✅ Manage tasks (CRUD + filter by status/priority + pagination search)
- ✅ Assign tasks to employees (one employee → many tasks)
- ✅ Global exception handling with custom error responses
- ✅ Entities mapped with **JPA/Hibernate** using lazy loading, cascading, and orphan removal
- ✅ Unit tests for service layer (EmployeeService, TaskService)
- ✅ Integrated **Swagger UI** for exploring and testing APIs

## 🧱 Tech Stack

- **Language**: Java
- **Framework**: Spring Boot
- **Security**: Spring Security, JWT, `BCryptPasswordEncoder`
- **Database**: (e.g.) MySQL (configurable)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Documentation**: Springdoc OpenAPI / Swagger UI


## 📁 Project Structure

```text
task-management-api
    │   .gitattributes
    │   .gitignore
    │   HELP.md
    │   mvnw
    │   mvnw.cmd
    │   pom.xml
    │
    ├───.mvn
    │   └───wrapper
    │           maven-wrapper.properties
    │
    ├───src
    │   ├───main
    │   │   ├───java
    │   │   │   └───com
    │   │   │       └───taskmanagement
    │   │   │           └───task_management_api
    │   │   │               │   TaskManagementApiApplication.java
    │   │   │               │
    │   │   │               ├───Controller
    │   │   │               │       AuthController.java
    │   │   │               │       EmployeeController.java
    │   │   │               │       TaskController.java
    │   │   │               │
    │   │   │               ├───DTO
    │   │   │               │       AuthRequest.java
    │   │   │               │       AuthResponse.java
    │   │   │               │       EmployeeRequest.java
    │   │   │               │       EmployeeResponse.java
    │   │   │               │       TaskRequest.java
    │   │   │               │       TaskResponse.java
    │   │   │               │
    │   │   │               ├───Entity
    │   │   │               │       Employee.java
    │   │   │               │       Permission.java
    │   │   │               │       Priority.java
    │   │   │               │       Role.java
    │   │   │               │       Task.java
    │   │   │               │       TaskStatus.java
    │   │   │               │       User.java
    │   │   │               │
    │   │   │               ├───Exception
    │   │   │               │       AuthenticationFailed.java
    │   │   │               │       EmployeeNotFoundException.java
    │   │   │               │       ErrorResponse.java
    │   │   │               │       GlobalExceptionHandler.java
    │   │   │               │       TaskNotFoundException.java
    │   │   │               │
    │   │   │               ├───Filter
    │   │   │               │       JwtAuthenticationFilter.java
    │   │   │               │
    │   │   │               ├───Kafka
    │   │   │               │       TaskEventConsumer.java
    │   │   │               │       TaskEventProducer.java
    │   │   │               │       TaskService.java
    │   │   │               │
    │   │   │               ├───Repository
    │   │   │               │       EmployeeRepository.java
    │   │   │               │       TaskRepository.java
    │   │   │               │       UserRepository.java
    │   │   │               │
    │   │   │               ├───SecurityConfig
    │   │   │               │       OpenApiConfig.java
    │   │   │               │       SecurityConfig.java
    │   │   │               │
    │   │   │               ├───Service
    │   │   │               │       CustomUserDetailsService.java
    │   │   │               │       EmployeeService.java
    │   │   │               │       TaskService.java
    │   │   │               │       UserService.java
    │   │   │               │
    │   │   │               └───Util
    │   │   │                       JwtUtil.java
    │   │   │
    │   │   └───resources
    │   │       │   application.properties
    │   │       │   application.yml
    │   │       │
    │   │       ├───static
    │   │       └───templates
    │   └───test
    │       └───java
    │           └───com
    │               └───taskmanagement
    │                   └───task_management_api
    │                           EmployeeServiceTest.java
    │                           TaskServiceTest.java

```

# 📌 API List (All Endpoints)

Below is the complete list of APIs available in this project.
<img width="1822" height="606" alt="Screenshot 2025-12-01 191808" src="https://github.com/user-attachments/assets/cfcda9f9-ca2d-4ec5-a672-5ea187997ba9" />
<img width="1828" height="628" alt="Screenshot 2025-12-01 191831" src="https://github.com/user-attachments/assets/4b880e1e-957b-45bd-875b-5ab47f1e4f08" />
<img width="1838" height="218" alt="Screenshot 2025-12-01 191851" src="https://github.com/user-attachments/assets/b5224e58-a814-4c7a-952b-cfea86256d95" />


## 🔧 How to Use the API with Swagger

We will use **Swagger UI** to test all APIs using the **default request bodies** that Swagger provides.

### 🔗 Swagger UI URL

Once the application is running, open:

```text
http://localhost:8080/swagger-ui/index.html
```


## ⚙️ Step 1 – Configure `application.properties`

Before running the project, update your database configuration in:

`src/main/resources/application.properties`

```
spring.datasource.url=REPLACE_WITH_YOUR_DB_URL
spring.datasource.username=REPLACE_WITH_YOUR_DB_USERNAME
spring.datasource.password=REPLACE_WITH_YOUR_DB_PASSWORD

```

> ✅ Make sure you update these credentials in application.properties with your actual database URL, username, and password.
> 

Then run the project (using your IDE or):

```bash
mvn spring-boot:run

```

---

## 🔑 Step 2 – Create User (`POST /signin/add`)

This endpoint is used to **create a user** (e.g. `ADMIN` or `USER`) who can later log in and get a JWT token.

**Endpoint:**

```
POST /signin/add

```

**Request Body (example):**

```json
{
  "username": "adminUser",
  "password": "admin123",
  "role": "ADMIN"
}

```

**Response Body (example):**

```json
{
  "username": "adminUser",
  "role": "ADMIN"
}

```

<img width="1436" height="647" alt="Screenshot 2025-12-01 192057" src="https://github.com/user-attachments/assets/0179b58e-4527-4b4a-a903-d0e762101f76" />



## 🔐 Step 3 – Get Token (`POST /signin/token`)

After creating the user, use this endpoint to **generate a JWT token**.

**Endpoint:**

```
POST /signin/token

```

**Request Body (example):**

```json
{
  "username": "adminUser",
  "password": "admin123",
  "role": "ADMIN"
}

```

**Response Body (example):**

```json
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

```

<img width="1452" height="721" alt="Screenshot 2025-12-01 192126" src="https://github.com/user-attachments/assets/18e3dc14-ed2b-434b-8254-59ac74c99145" />
<img width="1406" height="546" alt="Screenshot 2025-12-01 192146" src="https://github.com/user-attachments/assets/4246b300-feef-43a5-9261-041bc86eb525" />



## 🎫 Step 4 – Use Token in All Other APIs

Now you can call **Employee** and **Task** APIs using the generated token.

In **Swagger UI**:

1. Click the **"Authorize"** button (lock icon) at the top.
2. In the value field, enter:

```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

```

1. Click **"Authorize"** and then **"Close"**.

Swagger will now automatically send this token in the `Authorization` header for all secured APIs.

<img width="834" height="376" alt="Screenshot 2025-12-01 192208" src="https://github.com/user-attachments/assets/31ece12d-5476-4368-b52e-2b831b9f93d6" />


You can now use the **default request bodies** shown in Swagger for:

- Employee Management APIs (`/api/employees/...`)
- Task Management APIs (`/api/tasks/...`)


## 🧬 Domain Model

### Entities Overview

#### `User`
Used for **authentication and authorization**.

**Fields (example):**
- `Long id`
- `String username`
- `String password`
- `String role` (e.g. `ADMIN`, `USER`)

> Password is encoded with `BCryptPasswordEncoder` before persisting.


#### `Employee`
Represents a person who can be assigned multiple tasks.

**Fields (example):**
- `Long id`
- `String name`
- `String email`
- `String role` (e.g. `DEVELOPER`, `QA_ENGINEER`, etc.)

> Has a **one-to-many** relationship with `Task`.


#### `Task`
Represents a unit of work assigned to an employee.

**Fields (example):**
- `Long id`
- `String title`
- `TaskStatus status` (`NOT_STARTED`, `IN_PROGRESS`, `DONE`)
- `Priority priority` (`LOW`, `MEDIUM`, `HIGH`)
- `Employee employee`

> `Task` is the **owning side** of the Employee–Task relationship.


### Enums

- `TaskStatus`: `NOT_STARTED`, `IN_PROGRESS`, `DONE`
- `Priority`: `LOW`, `MEDIUM`, `HIGH`
- `Role` / `Permission`: for advanced role/permission handling.


## 🧾 DTO Layer

DTOs (Data Transfer Objects) are used to separate **API contracts** from **internal entities** and to avoid exposing entity internals directly.

### Auth DTOs

#### `AuthRequest`
Used for login and user creation.

**Fields:**
- `String username`
- `String password`
- `String role` (e.g. `ADMIN`, `USER`)

Used in:
- `POST /signin/add`
- `POST /signin/token`


#### `AuthResponse`
Returned after user creation or authentication.

**Typical Fields (example):**
- For `/signin/add`:
  - `String username`
  - `String role`
- For `/signin/token`:
  - `String token` (JWT string) – depending on how you implemented it


### Employee DTOs

#### `EmployeeRequest`
Incoming data when creating or updating an employee.

**Fields:**
- `String name`
- `String email`
- `String role`

Used in:
- `POST /api/employees`
- `PUT /api/employees/{id}`

#### `EmployeeResponse`
Data returned to the client when fetching employee information.

**Fields:**
- `Long id`
- `String name`
- `String email`
- `String role`

Used in:
- `GET /api/employees/{id}`
- `GET /api/employees`
- `POST /api/employees`
- `PUT /api/employees/{id}`


### Task DTOs

#### `TaskRequest`
Incoming data when creating or updating a task.

**Fields (example):**
- `String title`
- `TaskStatus status`
- `Priority priority`
- `Long employeeId` – used to link task to an employee

Used in:
- `POST /api/tasks`
- `PUT /api/tasks/{id}`


#### `TaskResponse`
Data returned to the client when fetching tasks.

**Fields (example):**
- `Long id`
- `String title`
- `TaskStatus status`
- `Priority priority`
- `EmployeeResponse employee` (or a subset of employee info)

Used in:
- `GET /api/tasks/{id}`
- `GET /api/tasks/by-status`
- `GET /api/tasks/by-priority`
- `GET /api/tasks/search`
- Employee-related task endpoints:
  - `GET /api/employees/{id}/tasks`
  - `GET /api/employees/{id}/tasks/by-status`


## 🔗 Relationships

### Employee–Task Relationship

**Type:** One-to-Many (One Employee → Many Tasks)

#### Task is the owning side:
- `@ManyToOne(fetch = FetchType.LAZY)`
- `@JoinColumn(name = "employee_id")`

#### Employee is the inverse side:
- `@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)`

<img width="588" height="290" alt="data table" src="https://github.com/user-attachments/assets/8906891d-19b7-4cba-9bb8-a41a74692a97" />

#### Helper methods keep both sides in sync, e.g.:

```java
public void addTask(Task task) {
    tasks.add(task);
    task.setEmployee(this);
}

public void removeTask(Task task) {
    tasks.remove(task);
    task.setEmployee(null);
}

---
```

## 🔐 Security (JWT Auth & Authorization)

Security is implemented in four main parts:

### 1. `SecurityConfig`

- Configures **Spring Security** filter chain.
- Protects all endpoints except:
    - `/signin/add`
    - `/signin/token`
- Applies role-based access (`ADMIN`, `USER`).
- Registers `JwtAuthenticationFilter` **before** the username/password authentication filter.
- Uses `BCryptPasswordEncoder` for password encoding.


### 2. `CustomUserDetailsService`

- Implements `UserDetailsService`.
- Loads `User` from the database using `username`.
- Used by Spring Security for authentication.


### 3. `JwtUtil`

Responsible for:

- Generating JWT tokens.
- Validating tokens.
- Extracting username and other claims.
- Ensuring that token is valid and not expired.


### 4. `JwtAuthenticationFilter`

- Extends `OncePerRequestFilter`.
- Runs on every request (except auth endpoints).

**Basic Steps:**

1. Checks `Authorization` header.
2. Extracts token (`Bearer <token>`).
3. Validates token via `JwtUtil`.
4. If valid, sets `Authentication` in `SecurityContext`.


### Roles and Permissions

### `ADMIN`

**Full access:**

- `GET`, `POST`, `PUT`, `DELETE` on Employee & Task APIs

### `USER`

**Limited access:**

- Can perform `GET` and `PUT` only
- Cannot perform `POST` (create) or `DELETE` operations


## 🧨 Exception Handling

Global exception handling is implemented to return **consistent error responses** to the client.

### Custom Exceptions

### `EmployeeNotFoundException`

Thrown when an employee with a given ID does not exist.

Example scenario:

- `GET /api/employees/{id}` with a non-existing ID.


### `TaskNotFoundException`

Thrown when a task with a given ID does not exist.

Example scenario:

- `GET /api/tasks/{id}` with a non-existing ID.


### `AuthenticationFailed`

Thrown when:

- Login credentials are invalid, or
- JWT token is invalid/expired (depending on implementation).


### `ErrorResponse` DTO

Used as a standard error body for exceptions handled by the global handler.

**Typical fields (example):**

- `LocalDateTime timestamp`
- `int status`
- `String error`
- `String message`
- `String path`

**Example JSON:**

```json
{
  "timestamp": "2025-01-01T10:00:00",
  "status": 404,
  "error": "Employee Not Found",
  "message": "Employee with id 1 not found",
  "path": "/api/employees/1"
}
```


### `GlobalExceptionHandler`

- Annotated with `@RestControllerAdvice`.
- Contains `@ExceptionHandler` methods for:
    - `EmployeeNotFoundException`
    - `TaskNotFoundException`
    - `AuthenticationFailed`
    - Generic `Exception` (optional)

Responsibilities:

- Catch exceptions thrown from controllers/service layer.
- Map them to proper HTTP status codes (e.g. `404`, `401`, `400`).
- Return an `ErrorResponse` object as JSON.

This ensures:

- Clean separation between business logic and error handling.
- Consistent error format across the entire API.

### Credits

Developed and maintained by **Sai Kumar**.

Special thanks to **Swagger / OpenAPI** for providing powerful tools for API documentation and testing.

Appreciation to the Spring Boot team and open-source community.

---
