# UI Guide

## Overview
The frontend is a single-page application built with vanilla HTML, CSS, and JavaScript. It provides a clean interface for profile management and order operations.

## User Interface Components

### 🔐 Authentication Section

#### Profile Creation Form
**Location**: Left side of main page

**Fields**:
- **User ID**: Numeric identifier (required)
- **Full Name**: User's display name (required)
- **Email**: Valid email address (required)
- **Password**: Account password (required)

**Validation**:
- Email format validation
- All fields required
- User ID must be positive number

#### Login Form
**Location**: Right side of main page

**Fields**:
- **Email**: Registered email address
- **Password**: Account password

**Behavior**:
- Validates credentials against backend
- Shows success/error messages
- Redirects to dashboard on success

### 📊 Dashboard Section
**Visibility**: Only shown after successful login

#### Header
- **Welcome Message**: Shows logged-in user's name
- **Logout Button**: Returns to authentication section

#### Create Order Form
**Purpose**: Initialize new orders

**Fields**:
- **Order ID**: Unique numeric identifier

**Behavior**:
- Automatically links order to logged-in user
- Creates empty order ready for items

#### Add Item Form
**Purpose**: Add products to existing orders

**Fields**:
- **Order ID**: Target order identifier
- **Product ID**: Product identifier
- **Quantity**: Number of items
- **SKU ID**: Stock keeping unit
- **Unit Price**: Price per item (decimal)
- **SGST Rate**: State GST percentage
- **CGST Rate**: Central GST percentage

**Validation**:
- All fields required
- Numeric validation for prices and rates
- Positive values only

#### Order Details Form
**Purpose**: Retrieve and display order information

**Fields**:
- **Order ID**: Order to retrieve

**Display**:
- Order summary (totals, dates, item count)
- Individual item details
- Tax breakdown per item

## User Workflows

### 🆕 New User Registration
1. Fill out "Create Profile" form
2. Click "Create Profile" button
3. See success message
4. Use same credentials to login

### 🔑 User Login
1. Enter email and password in "Login" form
2. Click "Login" button
3. Dashboard appears with welcome message
4. Authentication section hidden

### 📦 Order Creation
1. **Login required** - Dashboard must be visible
2. Enter unique Order ID in "Create New Order"
3. Click "Create Order"
4. Order created and linked to current user
5. Success message displayed

### 🛒 Adding Items
1. **Order must exist** - Create order first
2. Fill all fields in "Add Item to Order" form
3. Tax rates typically 9% each (SGST + CGST = 18%)
4. Click "Add Item"
5. Item added with automatic tax calculation

### 📋 Viewing Orders
1. Enter Order ID in "Get Order Details"
2. Click "Get Order"
3. Complete order information displayed:
   - Order summary with totals
   - Individual items with tax breakdown
   - Creation and update timestamps

## UI Features

### 📱 Responsive Design
- **Mobile-friendly**: Works on phones and tablets
- **Flexible layout**: Adapts to different screen sizes
- **Touch-friendly**: Large buttons and input fields

### 💬 User Feedback
- **Success Messages**: Green notifications for successful operations
- **Error Messages**: Red notifications for failures
- **Auto-hide**: Messages disappear after 3 seconds
- **Loading States**: Visual feedback during API calls

### 🎨 Visual Design
- **Clean Layout**: Minimal, professional appearance
- **Card-based**: Forms organized in white cards
- **Color Coding**: 
  - Blue: Primary actions and headers
  - Green: Success states
  - Red: Error states and logout
  - Gray: Secondary information

### 🔄 State Management
- **Session Handling**: Remembers logged-in user
- **Form Reset**: Clears forms after successful submission
- **Dynamic Visibility**: Shows/hides sections based on login state

## Technical Implementation

### 📁 File Structure
```
static/
├── index.html    # Main page structure
├── styles.css    # All styling rules
└── app.js        # JavaScript functionality
```

### 🔧 JavaScript Architecture
- **Event-driven**: Form submissions trigger API calls
- **Async/Await**: Modern promise handling
- **Error Handling**: Try-catch blocks for all API calls
- **DOM Manipulation**: Dynamic content updates

### 🎯 API Integration
- **RESTful**: Standard HTTP methods
- **JSON**: Request and response format
- **Error Handling**: Graceful failure management
- **CORS**: Cross-origin request support

## Customization

### 🎨 Styling Changes
Edit `styles.css`:
```css
/* Change primary color */
.form-container button {
    background-color: #28a745; /* Green instead of blue */
}

/* Modify card appearance */
.form-container {
    border-radius: 12px; /* More rounded corners */
    box-shadow: 0 4px 8px rgba(0,0,0,0.2); /* Stronger shadow */
}
```

### ⚙️ Behavior Modifications
Edit `app.js`:
```javascript
// Change message display duration
setTimeout(() => {
    messageDiv.style.display = 'none';
}, 5000); // 5 seconds instead of 3

// Add form validation
function validateOrderId(orderId) {
    if (orderId < 1 || orderId > 999999) {
        throw new Error('Order ID must be between 1 and 999999');
    }
}
```

## Browser Compatibility
- **Modern Browsers**: Chrome 60+, Firefox 55+, Safari 12+
- **ES6 Features**: Arrow functions, async/await, template literals
- **CSS Grid/Flexbox**: Modern layout techniques
- **Fetch API**: Native HTTP requests

## Accessibility
- **Semantic HTML**: Proper form labels and structure
- **Keyboard Navigation**: Tab order and focus management
- **Screen Readers**: ARIA labels where needed
- **Color Contrast**: Sufficient contrast ratios

## Future Enhancements
- **Form Validation**: Client-side validation before submission
- **Loading Spinners**: Visual feedback during API calls
- **Pagination**: For large order lists
- **Search/Filter**: Find specific orders
- **Dark Mode**: Alternative color scheme
- **PWA Features**: Offline capability and app-like experience