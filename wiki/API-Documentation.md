# API Documentation

## Profile Management APIs

### Create Profile
**Endpoint**: `POST /api/profiles`

**Request Body**:
```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Response**: `200 OK`
```
Profile created successfully
```

### Login
**Endpoint**: `POST /api/profiles/login`

**Request Body**:
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response**: `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "createdDate": "2024-01-15T10:30:00",
  "lastLoginDate": "2024-01-15T11:00:00",
  "active": true
}
```

### Get Profile
**Endpoint**: `GET /api/profiles/{userId}`

**Response**: `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "createdDate": "2024-01-15T10:30:00",
  "lastLoginDate": "2024-01-15T11:00:00",
  "active": true
}
```

## Order Management APIs

### Create Order
**Endpoint**: `POST /api/orders/{orderId}/user/{userId}`

**Response**: `200 OK`
```
Order created successfully
```

### Add Item to Order
**Endpoint**: `POST /api/orders/{orderId}/items`

**Request Body**:
```json
{
  "productId": 1,
  "quantity": 2,
  "skuId": "SKU001",
  "unitPrice": 100.0,
  "sgstRate": 9.0,
  "cgstRate": 9.0
}
```

**Response**: `200 OK`
```
Order item added successfully
```

### Get Order Details
**Endpoint**: `GET /api/orders/{orderId}`

**Response**: `200 OK`
```json
{
  "orderId": 123,
  "createdDate": "2024-01-15T10:30:00",
  "updatedDate": "2024-01-15T10:35:00",
  "itemCount": 2,
  "totalQuantity": 5,
  "subtotal": 500.00,
  "totalGST": 90.00,
  "total": 590.00,
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "skuId": "SKU001",
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
  ]
}
```

### Get Order Item Details
**Endpoint**: `GET /api/orders/{orderId}/items/{itemIndex}`

**Response**: `200 OK`
```json
{
  "productId": 1,
  "quantity": 2,
  "skuId": "SKU001",
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

## Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid email format"
}
```

### 401 Unauthorized
```json
{
  "error": "Invalid credentials"
}
```

### 404 Not Found
```json
{
  "error": "Order not found"
}
```

## API Usage Examples

### Complete Order Flow
```bash
# 1. Create Profile
curl -X POST http://localhost:8080/api/profiles \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"name":"John Doe","email":"john@example.com","password":"password123"}'

# 2. Login
curl -X POST http://localhost:8080/api/profiles/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'

# 3. Create Order
curl -X POST http://localhost:8080/api/orders/123/user/1

# 4. Add Item
curl -X POST http://localhost:8080/api/orders/123/items \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":2,"skuId":"SKU001","unitPrice":100.0,"sgstRate":9.0,"cgstRate":9.0}'

# 5. Get Order
curl http://localhost:8080/api/orders/123
```

## Rate Limits
- No rate limiting implemented
- Consider implementing for production use

## Authentication
- Simple password-based authentication
- No JWT tokens (enhancement opportunity)
- Session managed client-side