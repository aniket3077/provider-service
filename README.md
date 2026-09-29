# HomeServe Provider Service

Backend microservice for HomeServe service providers, handling authentication, profile management, skills & service mappings, daily availability scheduling, real-time geo-location, and performance telemetry.

## Features

- **Provider Identity & Auth:** JWT-based stateless authentication (`/api/auth/register`, `/api/auth/login`).
- **Profile Management:** View and update professional profile, years of experience, and bio (`/api/providers/profile`).
- **Service Mapping:** Add, remove, and toggle offered trade services (`/api/providers/services`).
- **Availability Scheduling:** Manage calendar availability time slots (`/api/providers/availability`).
- **Hyperlocal Location:** Update GPS coordinates (`/api/providers/location`).
- **Candidate Matching Endpoint:** Internal discovery query for matching providers based on service, time overlap, status, and Haversine radius (`/api/providers/matching-candidates`).
- **Performance & Metrics:** Ingest performance events and track acceptance, completion, and response rate metrics (`/internal/providers/performance/event`, `/api/providers/performance`).
- **Swagger / OpenAPI:** Interactive documentation at `/swagger-ui.html`.

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.8**
- **Spring Data JPA & Hibernate**
- **Spring Cloud Netflix Eureka**
- **PostgreSQL (Supabase)**
- **HikariCP**
- **Lombok**
- **SpringDoc OpenAPI**

## Setup & Running

```powershell
mvn clean compile
mvn spring-boot:run
```
Service runs on port `8082`.
