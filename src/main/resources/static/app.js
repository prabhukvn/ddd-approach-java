let currentUser = null;

// DOM Elements
const authSection = document.getElementById('auth-section');
const dashboardSection = document.getElementById('dashboard-section');
const messageDiv = document.getElementById('message');
const userNameSpan = document.getElementById('user-name');

// Forms
const createProfileForm = document.getElementById('create-profile-form');
const loginForm = document.getElementById('login-form');
const createOrderForm = document.getElementById('create-order-form');
const addItemForm = document.getElementById('add-item-form');
const getOrderForm = document.getElementById('get-order-form');
const logoutBtn = document.getElementById('logout-btn');

// Event Listeners
createProfileForm.addEventListener('submit', handleCreateProfile);
loginForm.addEventListener('submit', handleLogin);
createOrderForm.addEventListener('submit', handleCreateOrder);
addItemForm.addEventListener('submit', handleAddItem);
getOrderForm.addEventListener('submit', handleGetOrder);
logoutBtn.addEventListener('click', handleLogout);

// API Functions
async function createProfile(userData) {
    const response = await fetch('/api/profiles', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData)
    });
    return response;
}

async function login(credentials) {
    const response = await fetch('/api/profiles/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials)
    });
    return response;
}

async function createOrder(orderId, userId) {
    const response = await fetch(`/api/orders/${orderId}/user/${userId}`, {
        method: 'POST'
    });
    return response;
}

async function addOrderItem(orderId, itemData) {
    const response = await fetch(`/api/orders/${orderId}/items`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(itemData)
    });
    return response;
}

async function getOrder(orderId) {
    const response = await fetch(`/api/orders/${orderId}`);
    return response;
}

// Event Handlers
async function handleCreateProfile(e) {
    e.preventDefault();
    const userData = {
        userId: parseInt(document.getElementById('userId').value),
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    try {
        const response = await createProfile(userData);
        if (response.ok) {
            showMessage('Profile created successfully!', 'success');
            createProfileForm.reset();
        } else {
            showMessage('Failed to create profile', 'error');
        }
    } catch (error) {
        showMessage('Error creating profile', 'error');
    }
}

async function handleLogin(e) {
    e.preventDefault();
    const credentials = {
        email: document.getElementById('login-email').value,
        password: document.getElementById('login-password').value
    };

    try {
        const response = await login(credentials);
        if (response.ok) {
            const user = await response.json();
            currentUser = user;
            showDashboard();
            showMessage('Login successful!', 'success');
        } else {
            showMessage('Invalid credentials', 'error');
        }
    } catch (error) {
        showMessage('Login failed', 'error');
    }
}

async function handleCreateOrder(e) {
    e.preventDefault();
    const orderId = parseInt(document.getElementById('order-id').value);

    try {
        const response = await createOrder(orderId, currentUser.id);
        if (response.ok) {
            showMessage('Order created successfully!', 'success');
            createOrderForm.reset();
        } else {
            showMessage('Failed to create order', 'error');
        }
    } catch (error) {
        showMessage('Error creating order', 'error');
    }
}

async function handleAddItem(e) {
    e.preventDefault();
    const orderId = parseInt(document.getElementById('item-order-id').value);
    const itemData = {
        productId: parseInt(document.getElementById('product-id').value),
        quantity: parseInt(document.getElementById('quantity').value),
        skuId: document.getElementById('sku-id').value,
        unitPrice: parseFloat(document.getElementById('unit-price').value),
        sgstRate: parseFloat(document.getElementById('sgst-rate').value),
        cgstRate: parseFloat(document.getElementById('cgst-rate').value)
    };

    try {
        const response = await addOrderItem(orderId, itemData);
        if (response.ok) {
            showMessage('Item added successfully!', 'success');
            addItemForm.reset();
        } else {
            showMessage('Failed to add item', 'error');
        }
    } catch (error) {
        showMessage('Error adding item', 'error');
    }
}

async function handleGetOrder(e) {
    e.preventDefault();
    const orderId = parseInt(document.getElementById('get-order-id').value);

    try {
        const response = await getOrder(orderId);
        if (response.ok) {
            const order = await response.json();
            displayOrderDetails(order);
        } else {
            showMessage('Order not found', 'error');
        }
    } catch (error) {
        showMessage('Error fetching order', 'error');
    }
}

function handleLogout() {
    currentUser = null;
    showAuth();
    showMessage('Logged out successfully', 'success');
}

// UI Functions
function showAuth() {
    authSection.style.display = 'block';
    dashboardSection.style.display = 'none';
}

function showDashboard() {
    authSection.style.display = 'none';
    dashboardSection.style.display = 'block';
    userNameSpan.textContent = currentUser.name;
}

function showMessage(text, type) {
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    messageDiv.style.display = 'block';
    setTimeout(() => {
        messageDiv.style.display = 'none';
    }, 3000);
}

function displayOrderDetails(order) {
    const detailsDiv = document.getElementById('order-details');
    let html = `
        <h4>Order #${order.orderId}</h4>
        <p><strong>Items:</strong> ${order.itemCount} | <strong>Total Quantity:</strong> ${order.totalQuantity}</p>
        <p><strong>Subtotal:</strong> ₹${order.subtotal} | <strong>GST:</strong> ₹${order.totalGST} | <strong>Total:</strong> ₹${order.total}</p>
        <p><strong>Created:</strong> ${new Date(order.createdDate).toLocaleString()}</p>
    `;

    if (order.items && order.items.length > 0) {
        html += '<h5>Items:</h5>';
        order.items.forEach((item, index) => {
            html += `
                <div class="order-item">
                    <p><strong>Product ${item.productId}</strong> (SKU: ${item.skuId}) - Qty: ${item.quantity}</p>
                    <p>Unit Price: ₹${item.unitPrice} | Subtotal: ₹${item.subtotal} | Tax: ₹${item.totalTax} | Total: ₹${item.total}</p>
                    <div>
                        ${item.taxComponents.map(tax => 
                            `<span class="tax-component">${tax.name}: ${tax.rate}% (₹${tax.amount})</span>`
                        ).join('')}
                    </div>
                </div>
            `;
        });
    }

    detailsDiv.innerHTML = html;
}