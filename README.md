# AirBnb Full-Stack Clone 🏠✨

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Stripe](https://img.shields.io/badge/Stripe-Payments-6772E5?style=flat-square&logo=stripe)
![Docker](https://img.shields.io/badge/Docker-Container-blue?style=flat-square&logo=docker)
![Redis](https://img.shields.io/badge/Redis-Caching-DC382D?style=flat-square&logo=redis)

A full-featured Airbnb clone built with a robust **Spring Boot** backend. This project focuses
on high-availability, secure payment processing, and intelligent pricing models to simulate a
real-world vacation rental platform.

---

## 🚀 Key Features

* **Dynamic Pricing Strategy:** Implements an intelligent algorithm that adjusts property
  prices in real-time based on demand, seasonality, and local events.
* **Secure Payments with Stripe:** Full integration with **Stripe API** for handling bookings,
  refunds, and secure transaction processing.
* **AI-Powered Insights:** Integrated AI features for property recommendations or automated
  descriptions based on user preferences.
* **Advanced Caching:** Uses **Redis** for lightning-fast property searches and session
  management.
* **Security & Auth:** End-to-end security using **Spring Security** with JWT-based
  authentication and role-based access control (Host vs. Guest).
* **Global Exception Handling:** Centralized `@ControllerAdvice` to provide clean, consistent
  API error responses.
* **Containerized Deployment:** Optimized for **Docker** to ensure seamless deployment across
  any environment.

---

## 🛠️ Tech Stack

| Category           | Technology                                   |
|:-------------------|:---------------------------------------------|
| **Backend**        | Java 21, Spring Boot 3.x, Spring Data JPA    |
| **Security**       | Spring Security, JWT                         |
| **Payment**        | Stripe API                                   |
| **AI/ML**          | Spring AI / Custom Logic for Dynamic Pricing |
| **Database**       | PostgreSQL (Primary), Redis (Caching)        |
| **Infrastructure** | Docker, Docker Compose                       |
| **Error Handling** | Custom Global Exception Handlers             |

---

## 📂 Project Structure

```text
    AirBnb-Full-Stack
    ├── src/main/java/com/manish
    │   ├── advices/          # To Tracks all exceptions and handles all
    │   ├── auth/             # Secures application via Spring Security
    │   ├── config/           # Configurations for Stripe , Bean , Web Authentication
    │   ├── controller/       # REST endpoints (Bookings, Listings, Payments)
    │   ├── dto/              # Database Dto so that none can see original table , db name
    │   ├── entity/           # Database entities (User, Property, Reservation)
    │   ├── exception/        # Handles Exceptions
    │   └── repository/       # To talks to Database
    │   └── security/         # Classes to Handle Security like Token creation, refresh token etc.,
    │   └── service/          # Main class to do all operations using repository,entity,security etc.,
    │   └── strategy/         # Strategy Pattern for different pricing models
    │   └── util/             # Utility class to get all utils
    ├── Dockerfile            # Infrastructure (Postgres, Redis)
    └── pom.xml               # Maven dependencies
```

---

# 🏎️ Getting Started

## Prerequisites

* **Java 21+**
* **Stripe Developer Account (for API Keys)**
* **Docker Desktop**

## Clone the repo

```
    git clone [https://github.com/manishrnl/AirBnb-Full-Stack-Spring-Boot.git](https://github.com/manishrnl/AirBnb-Full-Stack-Spring-Boot.git)
```

# Build the project

```
    mvn clean package -DskipTests
```

# Start Infrastructure

```
docker-compose up -d
```

---

# 📈 Dynamic Pricing Logic

The core of this project is the Dynamic Pricing Engine. It calculates the nightly_rate by
analyzing:

* **Base Rate:** The standard price set by the host.

* **Demand Multiplier:** Higher rates during weekends or high-occupancy dates.

* **Seasonality:** Automatic price surges during peak vacation months.

# 🤝 Contributing

Found a bug or want to add a new AI feature? Pull requests are welcome!


---

# Maintained by Manish

---

