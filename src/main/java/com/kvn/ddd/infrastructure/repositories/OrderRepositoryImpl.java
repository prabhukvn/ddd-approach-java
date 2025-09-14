package com.kvn.ddd.infrastructure.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.kvn.ddd.domain.aggregates.Order;
import com.kvn.ddd.domain.repositories.OrderRepository;
import com.kvn.ddd.domain.valueobjects.Amount;
import com.kvn.ddd.domain.valueobjects.OrderId;
import com.kvn.ddd.domain.valueobjects.OrderItem;
import com.kvn.ddd.domain.valueobjects.ProductId;
import com.kvn.ddd.domain.valueobjects.Quantity;
import com.kvn.ddd.domain.valueobjects.TaxComponent;
import com.kvn.ddd.domain.valueobjects.TaxRate;
import com.kvn.ddd.domain.valueobjects.UserId;
import com.kvn.ddd.infrastructure.persistence.OrderEntity;
import com.kvn.ddd.infrastructure.persistence.OrderItemEntity;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    
    private final JpaOrderRepository jpaOrderRepository;

    public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return jpaOrderRepository.findById(orderId.getValue())
            .map(this::toDomain);
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        OrderEntity saved = jpaOrderRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void delete(OrderId orderId) {
        jpaOrderRepository.deleteById(orderId.getValue());
    }

    private Order toDomain(OrderEntity entity) {
        Order order = new Order(new OrderId(entity.getId()), new UserId(entity.getUserId()));
        entity.getOrderItems().forEach(itemEntity -> {
            Amount unitPrice = new Amount(itemEntity.getUnitPrice());
            List<TaxComponent> taxComponents = Arrays.asList(
                new TaxComponent("SGST", new TaxRate(itemEntity.getSgstRate()), unitPrice),
                new TaxComponent("CGST", new TaxRate(itemEntity.getCgstRate()), unitPrice)
            );
            OrderItem orderItem = new OrderItem(
                new ProductId(itemEntity.getProductId()),
                new Quantity(itemEntity.getQuantity()),
                itemEntity.getSkuId(),
                unitPrice,
                taxComponents
            );
            order.addOrderItem(orderItem);
        });
        return order;
    }

    private OrderEntity toEntity(Order order) {
        return new OrderEntity(
            order.getId().getValue(),
            order.getUserId().getValue(),
            order.getCreatedDate(),
            order.getUpdatedDate(),
            order.getOrderItems().stream()
                .map(item -> new OrderItemEntity(
                    item.getProductId().getValue(),
                    item.getQuantity().getValue(),
                    item.getSkuId(),
                    item.getUnitPrice().getValue(),
                    item.getTaxComponents().get(0).getRate().getPercentage(),
                    item.getTaxComponents().get(1).getRate().getPercentage()
                ))
                .collect(Collectors.toList())
        );
    }
}