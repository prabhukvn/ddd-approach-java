# Domain Model

## Aggregates

### 📦 Order Aggregate
**Root Entity**: `Order`

**Responsibilities**:
- Manage order lifecycle
- Maintain order items collection
- Calculate totals and taxes
- Enforce business rules (max 100 items)

**Properties**:
- `OrderId id` - Unique identifier
- `UserId userId` - Owner of the order
- `LocalDateTime createdDate` - Creation timestamp
- `LocalDateTime updatedDate` - Last modification
- `List<OrderItem> orderItems` - Items in the order

**Key Methods**:
- `addOrderItem(OrderItem)` - Add item with validation
- `calculateSubtotal()` - Sum of all item subtotals
- `calculateTotalGST()` - Sum of all item taxes
- `calculateTotal()` - Subtotal + GST

### 👤 Profile Aggregate
**Root Entity**: `Profile`

**Responsibilities**:
- Manage user authentication
- Track login activity
- Handle profile activation/deactivation

**Properties**:
- `UserId id` - Unique identifier
- `String name` - User's full name
- `Email email` - Validated email address
- `String passwordHash` - Encrypted password
- `LocalDateTime createdDate` - Registration date
- `LocalDateTime lastLoginDate` - Last login timestamp
- `boolean active` - Account status

**Key Methods**:
- `login()` - Update last login timestamp
- `activate()` / `deactivate()` - Manage account status

## Value Objects

### 💵 Amount
**Purpose**: Represent monetary values with validation

**Properties**:
- `BigDecimal value` - Monetary amount (non-negative)

**Methods**:
- `multiply(int quantity)` - Calculate total for quantity
- `add(Amount other)` - Sum two amounts

### 📧 Email
**Purpose**: Validated email addresses

**Properties**:
- `String value` - Email address (validated, lowercase)

**Validation**: Regex pattern for valid email format

### 🆔 UserId / OrderId / ProductId
**Purpose**: Type-safe identifiers

**Properties**:
- `Long value` - Numeric identifier (positive)

### 📊 TaxRate
**Purpose**: Percentage-based tax calculations

**Properties**:
- `BigDecimal percentage` - Tax rate (0-100%)

**Methods**:
- `calculateTax(Amount baseAmount)` - Compute tax amount

### 🧾 TaxComponent
**Purpose**: Individual tax line items (SGST, CGST)

**Properties**:
- `String name` - Tax type ("SGST", "CGST")
- `TaxRate rate` - Tax percentage
- `Amount amount` - Calculated tax amount

### 📦 OrderItem
**Purpose**: Individual items within an order

**Properties**:
- `ProductId productId` - Product identifier
- `Quantity quantity` - Number of items
- `String skuId` - Stock keeping unit
- `Amount unitPrice` - Price per unit
- `List<TaxComponent> taxComponents` - SGST and CGST

**Methods**:
- `calculateSubtotal()` - Unit price × quantity
- `calculateTotalTax()` - Sum of tax components × quantity
- `calculateTotal()` - Subtotal + total tax

### 🔢 Quantity
**Purpose**: Validated quantity values

**Properties**:
- `Integer value` - Number of items (positive)

## Domain Relationships

```
Profile (1) ←→ (Many) Order
    ↓
Order (1) ←→ (Many) OrderItem
    ↓
OrderItem (1) ←→ (Many) TaxComponent
```

## Business Invariants

### Order Invariants
- Order must have a valid user
- Maximum 100 items per order
- Order total = sum of item totals
- Update timestamp changes when items added

### Profile Invariants
- Email must be unique and valid
- Password must be hashed
- Active profiles can login
- Login updates last login timestamp

### Tax Invariants
- Tax rates between 0-100%
- Tax amount = base amount × rate ÷ 100
- Order GST = sum of all item taxes

## Domain Events (Future Enhancement)
- `OrderCreated`
- `OrderItemAdded`
- `ProfileRegistered`
- `UserLoggedIn`