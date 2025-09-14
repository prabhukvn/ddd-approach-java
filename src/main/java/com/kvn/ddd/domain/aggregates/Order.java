package com.kvn.ddd.domain.aggregates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kvn.ddd.domain.valueobjects.OrderId;
import com.kvn.ddd.domain.valueobjects.OrderItem;
import com.kvn.ddd.domain.valueobjects.UserId;
import com.kvn.ddd.domain.valueobjects.Amount;

public class Order {
    private OrderId id;
    private UserId userId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<OrderItem> orderItems;

    public Order(OrderId id, UserId userId) {
        this.id = id;
        this.userId = userId;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems.size() >= 100) {
            throw new IllegalStateException("Cannot add more than 100 items to an order");
        }
        orderItems.add(orderItem);
        this.updatedDate = LocalDateTime.now();
    }

    public OrderId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public int getItemCount() {
        return orderItems.size();
    }

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

    public Amount calculateTotal() {
        return orderItems.stream()
                .map(OrderItem::calculateTotal)
                .reduce(new Amount(0), Amount::add);
    }

    public int getTotalNumberOfItems() {
        return orderItems.stream()
                .mapToInt(item -> item.getQuantity().getValue())
                .sum();
    }
}