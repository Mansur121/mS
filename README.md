# 🧩 Microservices Architecture — Spring Boot

A production-ready microservices system built with Spring Boot and Spring Cloud. Features service discovery, API gateway, inter-service communication via Feign, and a full observability stack with Prometheus and Grafana.

---

## 🏗️ Architecture Overview

```
                        ┌──────────────────┐
                        │   API Gateway     │
                        │  (Spring Cloud)   │
                        │    Port: 8765     │
                        └────────┬─────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
   ┌──────────▼──────┐  ┌───────▼────────┐  ┌─────▼────────────┐
   │  Question-      │  │    Quiz-       │  │   (more services │
   │  Service        │  │    Service     │  │    coming soon)  │
   │  Port: 8082     │  │  Port: 8083    │  └──────────────────┘
   └──────────┬──────┘  └───────┬────────┘
              │                  │
              └────────┬─────────┘
                       │
          ┌────────────▼───────────┐
          │     Service Registry    │
          │  (Eureka Server)        │
          │     Port: 8761          │
          └─────────────────────────┘

    ┌──────────────────────────────────────────┐
    │           Observability Stack             │
    │   Prometheus (9090) → Grafana (3000)     │
    │   /actuator/prometheus on each service   │
    └──────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.x |
| Service Discovery | Spring Cloud Netflix Eureka |
| API Gateway | Spring Cloud Gateway |
| Inter-service Calls | OpenFeign |
| Database | MySQL + Spring Data JPA |
| Connection Pool | HikariCP |
| Metrics | Micrometer + Prometheus |
| Dashboards | Grafana |
| Logging | SLF4J + Logback |
| Build Tool | Maven |
| Language | Java 21 |

---

## 📦 Services

### 🟠 ServiceRegistry (Eureka Server)
All microservices register here on startup and discover each other by name — no hardcoded URLs.

- **Port:** `8761`
- **Dashboard:** `http://localhost:8761`

### 🔵 Apigateway
Single entry point for all client requests. Routes traffic to the correct microservice using service names from Eureka.

- **Port:** `8765`
- **Usage:** `http://localhost:8765/{service-route}/{endpoint}`

### 🟡 QuestionService
Manages the question bank used by quizzes.

- **Port:** `8082`
- **Metrics:** `http://localhost:8082/actuator/prometheus`

### 🔴 QuizService
Orchestrates quizzes. Calls QuestionService via OpenFeign to fetch questions.

- **Port:** `8083`
- **Metrics:** `http://localhost:8083/actuator/prometheus`

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8+
- Prometheus *(for metrics)*
- Grafana *(for dashboards)*

### 1. Clone the repository

```bash
git clone https://github.com/Mansur121/mS.git
cd mS
```

### 2. Configure MySQL

Create the required databases:

```sql
CREATE DATABASE questiondb;
CREATE DATABASE quizdb;
```

Update `application.properties` in each service:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/{dbname}
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Start services in this order

```bash
# 1. Eureka first — everything registers here
cd ServiceRegistry && mvn spring-boot:run

# 2. Core services
cd QuestionService && mvn spring-boot:run
cd QuizService     && mvn spring-boot:run

# 3. Gateway last
cd Apigateway      && mvn spring-boot:run
```

### 4. Verify

Open `http://localhost:8761` — all services should appear as **UP** in the Eureka dashboard.

---

## 📊 Observability

Every service is instrumented with **Micrometer** and exposes a Prometheus-compatible metrics endpoint.

### Actuator endpoints (per service)

```
GET /actuator/health      → is the service alive?
GET /actuator/metrics     → all available metric names
GET /actuator/prometheus  → raw metrics for Prometheus scraping
```

### Prometheus setup

Configure `prometheus.yml` to scrape all services:

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'question-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8082']

  - job_name: 'quiz-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8083']

  - job_name: 'api-gateway'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8765']
```

Start Prometheus:
```bash
./prometheus --config.file=prometheus.yml
# UI → http://localhost:9090/targets
```

### Grafana setup

1. Install Grafana → open `http://localhost:3000` (admin/admin)
2. Add data source: **Prometheus** → URL: `http://localhost:9090`
3. Import dashboard or paste custom JSON

### Useful PromQL queries

```promql
# HTTP requests per minute per service
rate(http_server_requests_seconds_count[5m]) * 60

# Error rate
rate(http_server_requests_seconds_count{outcome!="SUCCESS"}[5m])

# JVM heap used in MB
jvm_memory_used_bytes{area="heap"} / 1024 / 1024

# Average response time in ms
rate(http_server_requests_seconds_sum[5m])
  / rate(http_server_requests_seconds_count[5m]) * 1000

# DB connection pool active
hikaricp_connections_active
```

---

## 📁 Project Structure

```
mS/
├── ServiceRegistry/       # Eureka Server — service discovery
├── Apigateway/            # Spring Cloud Gateway — single entry point
├── QuestionService/       # Question management service
├── QuizService/           # Quiz orchestration service
└── README.md
```

---

## 🗺️ Roadmap

- [x] Service discovery with Eureka
- [x] API Gateway routing
- [x] Inter-service communication with OpenFeign
- [x] Metrics with Micrometer + Prometheus + Grafana
- [x] Structured logging with SLF4J + Logback
- [ ] Distributed tracing with OpenTelemetry + Zipkin
- [ ] Centralized log management with ELK Stack
- [ ] Circuit breaker with Resilience4j
- [ ] JWT authentication at the gateway
- [ ] Containerization with Docker
- [ ] Kubernetes deployment

---

## 👨‍💻 Author

**Mansur** — building production-grade Java backend systems, one microservice at a time.

[![GitHub](https://img.shields.io/badge/GitHub-Mansur121-black?logo=github)](https://github.com/Mansur121)
