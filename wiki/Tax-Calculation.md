# Tax Calculation System

## Overview
The system implements **Indian GST (Goods and Services Tax)** calculation with separate SGST and CGST components for intrastate transactions.

## GST Components

### 🏛️ SGST (State Goods and Services Tax)
- **Collected by**: State Government
- **Typical Rate**: 9% (for 18% total GST)
- **Applied to**: Intrastate transactions

### 🇮🇳 CGST (Central Goods and Services Tax)
- **Collected by**: Central Government  
- **Typical Rate**: 9% (for 18% total GST)
- **Applied to**: Intrastate transactions

### 📊 Total GST
- **Formula**: SGST + CGST = Total GST
- **Common Rates**: 5%, 12%, 18%, 28%
- **Split**: Usually equal between SGST and CGST

## Implementation Architecture

### 🏗️ Domain Model

#### TaxRate Value Object
```java
public class TaxRate {
    private final BigDecimal percentage; // 0-100%
    
    public Amount calculateTax(Amount baseAmount) {
        BigDecimal taxAmount = baseAmount.getValue()
            .multiply(percentage)
            .divide(BigDecimal.valueOf(100));
        return new Amount(taxAmount);
    }
}
```

#### TaxComponent Value Object
```java
public class TaxComponent {
    private final String name;        // "SGST" or "CGST"
    private final TaxRate rate;       // Tax percentage
    private final Amount amount;      // Calculated tax amount
    
    public TaxComponent(String name, TaxRate rate, Amount baseAmount) {
        this.name = name;
        this.rate = rate;
        this.amount = rate.calculateTax(baseAmount);
    }
}
```

#### OrderItem Integration
```java
public class OrderItem {
    private final List<TaxComponent> taxComponents;
    
    public Amount calculateTotalTax() {
        return taxComponents.stream()
            .map(TaxComponent::getAmount)
            .reduce(new Amount(0), Amount::add)
            .multiply(quantity.getValue());
    }
}
```

## Calculation Examples

### Example 1: Basic Item
**Item Details**:
- Unit Price: ₹100
- Quantity: 2
- SGST Rate: 9%
- CGST Rate: 9%

**Calculations**:
```
Subtotal = ₹100 × 2 = ₹200

SGST = ₹100 × 9% = ₹9 per unit
CGST = ₹100 × 9% = ₹9 per unit

Total Tax per unit = ₹9 + ₹9 = ₹18
Total Tax for quantity = ₹18 × 2 = ₹36

Final Total = ₹200 + ₹36 = ₹236
```

### Example 2: Multiple Items Order
**Order with 2 items**:

**Item 1**:
- Unit Price: ₹100, Quantity: 2
- SGST: 9%, CGST: 9%
- Subtotal: ₹200, Tax: ₹36, Total: ₹236

**Item 2**:
- Unit Price: ₹50, Quantity: 3  
- SGST: 6%, CGST: 6%
- Subtotal: ₹150, Tax: ₹18, Total: ₹168

**Order Totals**:
```
Order Subtotal = ₹200 + ₹150 = ₹350
Order GST = ₹36 + ₹18 = ₹54
Order Total = ₹350 + ₹54 = ₹404
```

## API Integration

### Adding Item with Tax
**Request**:
```json
POST /api/orders/123/items
{
  "productId": 1,
  "quantity": 2,
  "skuId": "SKU001",
  "unitPrice": 100.0,
  "sgstRate": 9.0,
  "cgstRate": 9.0
}
```

**Processing**:
```java
Amount unitPrice = new Amount(100.0);
List<TaxComponent> taxComponents = Arrays.asList(
    new TaxComponent("SGST", new TaxRate(9.0), unitPrice),
    new TaxComponent("CGST", new TaxRate(9.0), unitPrice)
);

OrderItem item = new OrderItem(productId, quantity, skuId, unitPrice, taxComponents);
```

### Response with Tax Details
```json
{
  "productId": 1,
  "quantity": 2,
  "unitPrice": 100.00,
  "subtotal": 200.00,
  "totalTax": 36.00,
  "total": 236.00,
  "taxComponents": [
    {
      "name": "SGST",
      "rate": 9.00,
      "amount": 9.00
    },
    {
      "name": "CGST", 
      "rate": 9.00,
      "amount": 9.00
    }
  ]
}
```

## Business Rules

### Tax Rate Validation
- **Range**: 0% to 100%
- **Precision**: Up to 2 decimal places
- **Common Rates**: 2.5%, 6%, 9%, 14%

### Calculation Rules
1. **Tax Base**: Unit price (excluding tax)
2. **Per Unit**: Tax calculated on unit price
3. **Quantity Multiplication**: Tax amount × quantity
4. **Rounding**: Standard rounding rules apply

### Order Level Aggregation
```java
public Amount calculateTotalGST() {
    return orderItems.stream()
        .map(OrderItem::calculateTotalTax)
        .reduce(new Amount(0), Amount::add);
}
```

## Tax Compliance Features

### 🧾 Tax Breakdown
- Individual tax components displayed
- Rate and amount shown separately
- Clear SGST/CGST distinction

### 📊 Reporting Ready
- Tax amounts stored separately
- Easy aggregation for tax returns
- Audit trail maintained

### 🔍 Transparency
- All calculations visible to users
- Tax rates explicitly shown
- No hidden tax components

## Configuration Options

### Default Tax Rates
```java
public class TaxConfiguration {
    public static final double DEFAULT_SGST_RATE = 9.0;
    public static final double DEFAULT_CGST_RATE = 9.0;
    public static final double TOTAL_GST_RATE = 18.0;
}
```

### Product-Specific Rates
```java
// Future enhancement: Tax rates by product category
public enum ProductCategory {
    ESSENTIAL(5.0),      // 2.5% SGST + 2.5% CGST
    STANDARD(18.0),      // 9% SGST + 9% CGST  
    LUXURY(28.0);        // 14% SGST + 14% CGST
}
```

## Future Enhancements

### 🌍 IGST Support
- **Interstate transactions**: Single IGST instead of SGST+CGST
- **Rate**: Same total rate (18% IGST vs 9% SGST + 9% CGST)
- **Implementation**: Additional TaxComponent type

### 📋 HSN Code Integration
- **Product Classification**: HSN (Harmonized System of Nomenclature)
- **Automatic Tax Rates**: Based on HSN code
- **Compliance**: Required for GST returns

### 🧮 Reverse Charge Mechanism
- **B2B Transactions**: Buyer pays tax instead of seller
- **Service Providers**: Specific scenarios
- **Implementation**: Flag in OrderItem

### 📈 Tax Rate History
- **Rate Changes**: Track historical tax rates
- **Effective Dates**: When rates changed
- **Audit Trail**: Complete tax calculation history