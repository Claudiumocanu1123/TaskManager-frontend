# TaskManager Pro - Backend

Backend REST API for the TaskManager Pro application built with Spring Boot.

## Overview

TaskManager Pro is a task management application that allows users to create, update, delete, and manage tasks efficiently through a RESTful API.

This backend handles:
- Task CRUD operations
- REST API communication
- JSON serialization/deserialization
- Data persistence
- Client-server interaction

---

## Features

- Create new tasks
- Update existing tasks
- Delete tasks
- Retrieve all tasks
- Retrieve task by ID
- RESTful API architecture
- JSON request/response handling
- Maven project structure
- Clean layered architecture

---

## Technologies Used

- Java
- Spring Boot
- Maven
- Jackson
- REST API
- HTTP Client
- IntelliJ IDEA

---

## Project Structure

```text
src/
 ├── main/
 │   ├── java/
 │   │    └── com/taskmanager/
 │   │          ├── controller/
 │   │          ├── model/
 │   │          ├── service/
 │   │          └── repository/
 │   └── resources/
 │         └── application.properties