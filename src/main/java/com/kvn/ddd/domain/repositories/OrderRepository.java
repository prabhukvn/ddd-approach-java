package com.kvn.ddd.domain.repositories;

import java.util.Optional;

import com.kvn.ddd.domain.aggregates.Order;
import com.kvn.ddd.domain.valueobjects.OrderId;

public interface OrderRepository {
    Optional<Order> findById(OrderId orderId);
    Order save(Order order);
    void delete(OrderId orderId);
}