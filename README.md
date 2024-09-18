
# Expense-Tracker

Expense-Tracker is a simple web application that helps users track their expenses and incomes. It allows users to categorize their expenses, view their spending history, and set monthly budgets. Built with Java Spring Boot and PostgreSQL, the app provides a secure and scalable solution for personal financial management.

---

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [JWT Authentication](#jwt-authentication)
- [Swagger Documentation](#swagger-documentation)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **User Authentication**: Secure login and registration using JWT authentication.
- **Expense Management**: Add, edit, delete, and view expenses.
- **Income Management**: Track and manage multiple income sources.
- **Expense Categories**: Categorize expenses into predefined or custom categories (e.g., groceries, entertainment, utilities).
- **Monthly Budgeting**: Set and manage monthly budgets for different categories.
- **Spending Reports**: Visual representation of monthly spending, categorized expenses, and budget adherence.
- **Secure API**: All sensitive endpoints are protected by JWT tokens to ensure user data security.
- **API Documentation**: Swagger UI provides a convenient interface for API exploration and testing.

---

## Technologies

- **Java 17**
- **Spring Boot 3.x**
  - Spring Data JPA (for ORM)
  - Spring Security (for authentication)
  - Spring Web (for building RESTful APIs)
- **PostgreSQL** (for database management)
- **JWT (JSON Web Tokens)** (for authentication)
- **Hibernate** (for ORM and database schema management)
- **Swagger (SpringFox)** (for API documentation)
- **Docker** (optional, for containerized deployment)

---

## Requirements

Before you begin, ensure you have the following installed on your system:

- **Java 21**
- **PostgreSQL** (version 13 or higher)
- **Maven** (for managing dependencies)
- **Docker** (optional, for containerization)

---

## Installation

### 1. Clone the repository:

```bash
git clone https://github.com/your-username/expense-tracker.git
cd expense-tracker
```
![Expense-Tracker ](https://github.com/user-attachments/assets/f7dab56b-e624-4d1a-8fe9-b235ea0791e2)

## Set Up PostgreSQL:
Ensure that PostgreSQL is installed and running on your system. Create a database named Expense_Tracker.

```bash
CREATE DATABASE Expense_Tracker;
```


## Configure the Application:
Update the src/main/resources/application.properties file with your PostgreSQL credentials and other configuration options.


```bash
spring.application.name=Expense-Tracker
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/Expense_Tracker
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA and Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# JWT Settings
spring.app.jwtSecret=your_jwt_secret
spring.app.jwtExpirationMs=3000000
spring.ecom.app.jwtCookieName=springBootEcom

# Enable Swagger
springfox.documentation.swagger-ui.enabled=true

```

## 4. Build the Project

Use Maven to build the project:

```bash
mvn clean install
```

# Running the Application

#### Option 1: Run Locally
Start the application using Maven:

```bash  
mvn spring-boot:run

```

#### Option 2: Run with Docker
To run the application with Docker, follow these steps:
```bash
# Build the Docker image
docker build -t expense-tracker .

# Run the PostgreSQL container
docker run --name postgres -e POSTGRES_DB=Expense_Tracker -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=your_password -p 5432:5432 -d postgres

# Run the Expense-Tracker container
docker run --name expense-tracker --link postgres -p 8081:8081 -d expense-tracker

```

## Swagger Documentation
Swagger UI is integrated for exploring the APIs. You can access it at:




We welcome contributions! To contribute:

1. **Fork the repository.**
2. **Create a new branch:**
   ```bash
   git checkout -b feature-branch
3. **Commit your changes**
   ```js
   git commit -m 'Add some feature'
```
4. **Push to the branch:**
```bash git push origin feature-branch
```
5. **Open a pull request.**
   Please ensure your code follows the project standards and includes relevant tests.
