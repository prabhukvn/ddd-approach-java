# DDD Architecture

## Architectural Layers

### 🏛️ Domain Layer (`com.kvn.ddd.domain`)
**Purpose**: Core business logic and rules

**Components**:
- **Aggregates**: `Order`, `Profile`
- **Value Objects**: `Amount`, `Email`, `TaxRate`, `OrderItem`
- **Repositories**: Interfaces for data access
- **Domain Services**: Complex business logic

**Principles**:
- No dependencies on other layers
- Rich domain model with behavior
- Encapsulates business rules
- Immutable value objects

**Key Files**:
```
domain/
├── aggregates/
│   ├── Order.java
│   └── Profile.java
├── valueobjects/
│   ├── Amount.java
│   ├── Email.java
│   ├── OrderItem.java
│   ├── TaxComponent.java
│   └── TaxRate.java
└── repositories/
    ├── OrderRepository.java
    └── ProfileRepository.java
```

### 🔄 Application Layer (`com.kvn.ddd.application`)
**Purpose**: Orchestrate domain objects and coordinate use cases

**Components**:
- **Application Services**: Use case implementations
- **DTOs**: Data transfer objects for external communication
- **Command/Query Handlers**: CQRS pattern (future)

**Responsibilities**:
- Transaction boundaries
- Security enforcement
- DTO mapping
- Use case coordination

**Key Files**:
```
application/
├── services/
│   ├── OrderApplicationService.java
│   └── ProfileApplicationService.java
└── dto/
    ├── OrderDto.java
    ├── OrderItemDto.java
    ├── ProfileDto.java
    └── TaxComponentDto.java
```

### 🏗️ Infrastructure Layer (`com.kvn.ddd.infrastructure`)
**Purpose**: Technical implementation details

**Components**:
- **Persistence**: JPA entities and repositories
- **External Services**: Third-party integrations
- **Configuration**: Technical setup

**Responsibilities**:
- Database operations
- External API calls
- File system access
- Messaging

**Key Files**:
```
infrastructure/
├── persistence/
│   ├── OrderEntity.java
│   ├── OrderItemEntity.java
│   └── ProfileEntity.java
└── repositories/
    ├── JpaOrderRepository.java
    ├── OrderRepositoryImpl.java
    ├── JpaProfileRepository.java
    └── ProfileRepositoryImpl.java
```

### 🌐 Interface Layer (`com.kvn.ddd.interfaces`)
**Purpose**: External communication and user interfaces

**Components**:
- **REST Controllers**: HTTP endpoints
- **Web UI**: Frontend interfaces
- **CLI**: Command-line interfaces (future)

**Responsibilities**:
- HTTP request/response handling
- Input validation
- Response formatting
- CORS configuration

**Key Files**:
```
interfaces/
└── controllers/
    ├── OrderController.java
    └── ProfileController.java
```

## Design Patterns

### 🏛️ Aggregate Pattern
**Implementation**: `Order` and `Profile` aggregates

**Benefits**:
- Consistency boundaries
- Transaction boundaries
- Encapsulation of business rules

**Example**:
```java
public class Order {
    private OrderId id;
    private UserId userId;
    private List<OrderItem> orderItems;
    
    public void addOrderItem(OrderItem item) {
        // Business rule: max 100 items
        if (orderItems.size() >= 100) {
            throw new IllegalStateException("Cannot add more than 100 items");
        }
        orderItems.add(item);
        this.updatedDate = LocalDateTime.now();
    }
}
```

### 💎 Value Object Pattern
**Implementation**: `Amount`, `Email`, `TaxRate`

**Benefits**:
- Immutability
- Self-validation
- Rich behavior

**Example**:
```java
public class Email {
    private final String value;
    
    public Email(String value) {
        if (!isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value.toLowerCase();
    }
}
```

### 🗄️ Repository Pattern
**Implementation**: `OrderRepository`, `ProfileRepository`

**Benefits**:
- Data access abstraction
- Testability
- Domain-focused queries

**Example**:
```java
public interface OrderRepository {
    Optional<Order> findById(OrderId id);
    Order save(Order order);
    void delete(OrderId id);
}
```

### 🔄 Application Service Pattern
**Implementation**: `OrderApplicationService`, `ProfileApplicationService`

**Benefits**:
- Use case coordination
- Transaction management
- DTO conversion

**Example**:
```java
@Service
public class OrderApplicationService {
    public void addItemToOrder(Long orderId, ...) {
        Order order = orderRepository.findById(new OrderId(orderId))
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        OrderItem item = new OrderItem(...);
        order.addOrderItem(item);
        orderRepository.save(order);
    }
}
```

## Dependency Direction

```
Interface Layer
       ↓
Application Layer
       ↓
Domain Layer
       ↑
Infrastructure Layer
```

**Key Principle**: Dependencies point inward toward the domain

## Benefits of This Architecture

### 🧪 Testability
- Domain logic isolated and testable
- Mock repositories for unit tests
- Integration tests at application layer

### 🔧 Maintainability
- Clear separation of concerns
- Business logic centralized in domain
- Technical details isolated in infrastructure

### 🚀 Flexibility
- Easy to change persistence technology
- Simple to add new interfaces (CLI, GraphQL)
- Domain remains stable as requirements evolve

### 📈 Scalability
- Clear boundaries for microservices extraction
- Independent scaling of layers
- Caching strategies at appropriate layers