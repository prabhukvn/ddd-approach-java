# Order Management

## Overview
The order management system handles the complete lifecycle of orders from creation to completion, with integrated tax calculations and user ownership.

## Order Lifecycle

### 📋 Order States
```
Created → Items Added → Completed
   ↓           ↓           ↓
Empty      Populated   Finalized
```

**State Descriptions**:
- **Created**: Empty order linked to user
- **Items Added**: Order contains one or more items
- **Completed**: Order ready for processing (future state)

## Core Operations

### 🆕 Order Creation
**Purpose**: Initialize new order for a user

**Process**:
1. Validate user exists and is active
2. Generate unique order ID
3. Create empty order linked to user
4. Set creation timestamp

**API Endpoint**:
```
POST /api/orders/{orderId}/user/{userId}
```

**Domain Logic**:
```java
public Order createOrder(Long orderId, Long userId) {
    OrderId orderIdVO = new OrderId(orderId);
    UserId userIdVO = new UserId(userId);
    Order order = new Order(orderIdVO, userIdVO);
    return orderRepository.save(order);
}
```

### 🛒 Item Addition
**Purpose**: Add products to existing orders

**Process**:
1. Validate order exists
2. Create OrderItem with tax components
3. Apply business rules (max 100 items)
4. Update order timestamp
5. Persist changes

**Business Rules**:
- Maximum 100 items per order
- All item fields required
- Tax rates must be valid (0-100%)
- Unit price must be positive

**Domain Logic**:
```java
public void addOrderItem(OrderItem orderItem) {
    if (orderItems.size() >= 100) {
        throw new IllegalStateException("Cannot add more than 100 items");
    }
    orderItems.add(orderItem);
    this.updatedDate = LocalDateTime.now();
}
```

### 📊 Order Calculations
**Purpose**: Compute order totals and taxes

**Calculations**:
1. **Subtotal**: Sum of all item subtotals
2. **Total GST**: Sum of all item taxes
3. **Grand Total**: Subtotal + Total GST
4. **Item Count**: Number of distinct items
5. **Total Quantity**: Sum of all quantities

**Implementation**:
```java
public Amount calculateSubtotal() {
    return orderItems.stream()
        .map(OrderItem::calculateSubtotal)
        .reduce(new Amount(0), Amount::add);
}

public Amount calculateTotalGST() {
    return orderItems.stream()
        .map(OrderItem::calculateTotalTax)
        .reduce(new Amount(0), Amount::add);
}
```

## Item Management

### 🏷️ OrderItem Structure
**Components**:
- **Product Information**: ID, SKU, quantity
- **Pricing**: Unit price, subtotal
- **Tax Details**: SGST and CGST components
- **Totals**: Tax amount, final total

**Tax Calculation**:
```java
public Amount calculateTotalTax() {
    return taxComponents.stream()
        .map(TaxComponent::getAmount)
        .reduce(new Amount(0), Amount::add)
        .multiply(quantity.getValue());
}
```

### 📦 Item Validation
**Rules**:
- Product ID must be positive
- Quantity must be positive
- SKU ID cannot be empty
- Unit price must be positive
- Tax rates between 0-100%

**Implementation**:
```java
public OrderItem(ProductId productId, Quantity quantity, String skuId, 
                Amount unitPrice, List<TaxComponent> taxComponents) {
    this.productId = Objects.requireNonNull(productId, "ProductId cannot be null");
    this.quantity = Objects.requireNonNull(quantity, "Quantity cannot be null");
    this.skuId = Objects.requireNonNull(skuId, "SkuId cannot be null");
    this.unitPrice = Objects.requireNonNull(unitPrice, "Unit price cannot be null");
    this.taxComponents = Objects.requireNonNull(taxComponents, "Tax components cannot be null");
}
```

## User Ownership

### 🔐 Order-Profile Relationship
**Relationship**: One Profile → Many Orders

**Benefits**:
- **Access Control**: Users can only access their orders
- **Order History**: Track user's purchase history
- **Personalization**: User-specific features
- **Audit Trail**: Who created which orders

**Implementation**:
```java
public class Order {
    private UserId userId;  // Links order to profile
    
    public Order(OrderId id, UserId userId) {
        this.id = id;
        this.userId = userId;
        // ...
    }
}
```

### 🔍 Order Retrieval
**Current**: By Order ID only
**Future Enhancement**: By User ID

```java
// Future method
public List<OrderDto> getUserOrders(Long userId) {
    UserId userIdVO = new UserId(userId);
    List<Order> orders = orderRepository.findByUserId(userIdVO);
    return orders.stream()
        .map(this::mapToOrderDto)
        .collect(Collectors.toList());
}
```

## Business Rules

### 📏 Order Constraints
1. **Item Limit**: Maximum 100 items per order
2. **User Required**: Every order must have an owner
3. **Unique ID**: Order IDs must be unique
4. **Positive Values**: All monetary amounts positive
5. **Valid Dates**: Creation date ≤ Update date

### 💰 Financial Rules
1. **Tax Calculation**: Tax = Unit Price × Rate ÷ 100
2. **Quantity Impact**: Tax amount × quantity
3. **Rounding**: Standard rounding to 2 decimal places
4. **Currency**: All amounts in same currency (₹)

### 🔒 Security Rules
1. **User Validation**: User must exist and be active
2. **Order Access**: Users can only access their orders
3. **Data Integrity**: All required fields must be present

## API Operations

### Order Creation
```bash
curl -X POST http://localhost:8080/api/orders/123/user/1
```

### Add Item
```bash
curl -X POST http://localhost:8080/api/orders/123/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2,
    "skuId": "SKU001",
    "unitPrice": 100.0,
    "sgstRate": 9.0,
    "cgstRate": 9.0
  }'
```

### Get Order
```bash
curl http://localhost:8080/api/orders/123
```

### Get Specific Item
```bash
curl http://localhost:8080/api/orders/123/items/0
```

## Error Handling

### Common Errors
- **Order Not Found**: Invalid order ID
- **Item Limit Exceeded**: More than 100 items
- **Invalid User**: User doesn't exist or inactive
- **Validation Errors**: Invalid input data

### Error Responses
```json
{
  "error": "Order not found",
  "code": "ORDER_NOT_FOUND",
  "timestamp": "2024-01-15T12:00:00Z"
}
```

## Performance Considerations

### 🚀 Optimization Strategies
1. **Lazy Loading**: Load items only when needed
2. **Caching**: Cache frequently accessed orders
3. **Batch Operations**: Add multiple items at once
4. **Database Indexing**: Index on user_id and created_date

### 📊 Monitoring Metrics
- Order creation rate
- Average items per order
- Order processing time
- Error rates by operation

## Future Enhancements

### 🔄 Order Status Management
```java
public enum OrderStatus {
    DRAFT, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}
```

### 📦 Inventory Integration
- Check product availability
- Reserve items on order creation
- Update inventory on completion

### 💳 Payment Integration
- Payment method selection
- Payment processing
- Payment status tracking

### 📧 Notifications
- Order confirmation emails
- Status update notifications
- Delivery notifications

### 📈 Analytics
- Order trends and patterns
- Popular products
- User behavior analysis
- Revenue reporting