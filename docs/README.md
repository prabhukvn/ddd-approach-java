# C4 Model Diagrams for FastCart Order System

This directory contains C4 model diagrams for the FastCart Order Management System following DDD principles.

## Diagrams

### Level 1: System Context
- **File**: `c4-level1-context.puml`
- **Shows**: High-level system interactions
- **Audience**: Everyone

### Level 2: Container
- **File**: `c4-level2-container.puml`  
- **Shows**: Application containers and their relationships
- **Audience**: Technical stakeholders

### Level 3: Component
- **File**: `c4-level3-component.puml`
- **Shows**: Internal components within the Spring Boot application
- **Audience**: Software architects, developers

### Level 4: Code
- **File**: `c4-level4-code.puml`
- **Shows**: DDD structure with domain, application, infrastructure, and interface layers
- **Audience**: Developers

## How to View

1. Install PlantUML extension in your IDE
2. Open `.puml` files to view diagrams
3. Or use online PlantUML viewer: http://www.plantuml.com/plantuml/

## DDD Architecture Highlights

- **Domain Layer**: Pure business logic (Order, OrderItem, Value Objects)
- **Application Layer**: Orchestration (OrderApplicationService)
- **Infrastructure Layer**: Technical concerns (JPA, Database)
- **Interface Layer**: External communication (REST Controllers)