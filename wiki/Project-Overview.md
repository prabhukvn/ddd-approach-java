# Project Overview

## Purpose
This project demonstrates a **Domain-Driven Design (DDD)** implementation for an order management system with integrated profile management and Indian GST tax calculations.

## Key Features

### 🔐 Profile Management
- User registration and authentication
- Profile creation with email validation
- Login/logout functionality
- Session management

### 📦 Order Management
- Create orders linked to user profiles
- Add items with detailed pricing
- Tax calculation (SGST + CGST)
- Order history and details

### 💰 Tax System
- **SGST** (State Goods and Services Tax)
- **CGST** (Central Goods and Services Tax)
- Automatic tax calculation per item
- Total GST aggregation at order level

## Business Rules

### Order Rules
- Maximum 100 items per order
- Each order belongs to a specific user
- Orders track creation and update timestamps

### Tax Rules
- Tax rates are configurable per item
- Tax calculated on unit price × quantity
- Total order tax = sum of all item taxes

### Profile Rules
- Unique email addresses
- Password hashing for security
- Profile activation/deactivation

## Architecture Principles

### Domain-Driven Design
- **Aggregates**: Order, Profile
- **Value Objects**: Amount, Email, UserId, TaxRate
- **Repositories**: Data access abstraction
- **Services**: Business logic coordination

### Clean Architecture
- **Domain Layer**: Core business logic
- **Application Layer**: Use cases and DTOs
- **Infrastructure Layer**: Database and external concerns
- **Interface Layer**: REST controllers and UI

## Technology Choices

### Backend
- **Spring Boot**: Application framework
- **JPA/Hibernate**: ORM for database operations
- **H2 Database**: In-memory database for simplicity

### Frontend
- **Vanilla JavaScript**: No framework dependencies
- **Responsive CSS**: Mobile-friendly design
- **REST API Integration**: Async operations

## Project Structure
```
src/main/java/com/kvn/ddd/
├── domain/           # Core business logic
├── application/      # Use cases and DTOs
├── infrastructure/   # Database and persistence
├── interfaces/       # REST controllers
└── config/          # Configuration classes

src/main/resources/
└── static/          # Frontend files (HTML, CSS, JS)
```