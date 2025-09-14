package com.kvn.ddd.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kvn.ddd.infrastructure.persistence.OrderEntity;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}