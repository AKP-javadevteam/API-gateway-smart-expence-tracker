 commi# API Gateway — Smart Expense Project

This is the **API Gateway** microservice for the Smart Expense project.  
It serves as the single entry point for all client requests and handles **routing, security, validation, and observability**.

---

## 🚀 Tech Stack

- **Project**: Maven, Java 17
- **Spring Boot**: 3.5.4

### Dependencies
- **Spring Web** — provides the web layer and supports Spring Cloud Gateway.
- **Spring Cloud Gateway** — reactive API Gateway for routing and filters.
- **Spring Security** — authentication and authorization.
- **Validation** — for input validation (DTOs, request parameters).
- **Spring Boot Actuator** — monitoring and health checks.
---

## 📂 Project Purpose
- Provide a **centralized gateway** for all backend microservices.
- Handle **authentication & authorization** using Spring Security.
- Validate incoming requests using Spring Validation.
- Expose **health check and monitoring endpoints** via Actuator.
