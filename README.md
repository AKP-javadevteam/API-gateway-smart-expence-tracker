# API Gateway — Smart Expense Project

This is the **API Gateway** microservice for the Smart Expense & Cashflow Tracker.  
It serves as the single entry point for all client requests and handles **routing, security, validation, and observability**.

---

## 🚀 Tech Stack

- **Project:** Maven, Java 17
- **Spring Boot:** 3.x (Reactive)
- **Spring Cloud:** Spring Cloud Gateway
- **Security:** Spring Security (JWT Resource Server)
- **Ops:** Spring Boot Actuator, (optional) Redis rate limiting

---

## 📂 Responsibilities

- **Central routing** to backend microservices:
    - `/auth/**` → Auth Service
    - `/users/**`, `/budgets/**` → Users & Budgets Service
    - `/transactions/**`, `/imports/**` → Transactions & Import Service
    - `/categorize/**`, `/rules/**`, `/categories/**` → Categorization & Rules Service
    - `/forecast/**`, `/insights/**`, `/reports/**` → Analytics Service
- **Security**: Validate **JWT** at the edge; pass through to downstream services.
- **CORS**: Central, consistent policy for web clients.
- **Observability**: Correlation ID propagation and Actuator health.

---

## 🧱 Project Structure
com.example.gateway
├─ ApiGatewayApplication.java
├─ config
│ ├─ SecurityConfig.java # JWT validation; public /auth/**
│ ├─ CorsConfig.java # Global CORS
│ └─ ObservabilityConfig.java # IP key resolver for rate limiting
├─ filter
│ ├─ CorrelationIdFilter.java # Adds/propagates X-Correlation-Id
│ └─ FallbackController.java # /fallback for circuit breaker
└─ util
└─ Constants.java # Shared header/path constants
---

---

## ⚙️ Configuration

All main settings live in `application.yml`.

**Environment variables (per env):**
- `ROUTE_AUTH`, `ROUTE_USERS`, `ROUTE_TRANSACTIONS`, `ROUTE_CATEGORIZE`, `ROUTE_ANALYTICS`
- **JWT**: either `JWT_SECRET` (HS256) or `AUTH_JWKS_URI` 

---
## 🔒 Security Notes

- Gateway is a JWT Resource Server. It validates tokens and forwards requests.
- Keep /auth/** publicly accessible to allow login/refresh flows.
- Downstream services should also validate JWT (defense in depth).

## 🧪 Observability

- Actuator: /actuator/health, /actuator/info, /actuator/metrics
- Correlation ID: header X-Correlation-Id is added if missing and returned in responses.

## 🚧 Optional Features

- Circuit Breaker: enable per-route with CircuitBreaker filter and fallbackUri: forward:/fallback/....
- Rate Limiting: enable RequestRateLimiter with Redis and an ipKeyResolver bean.

## 📜 License





