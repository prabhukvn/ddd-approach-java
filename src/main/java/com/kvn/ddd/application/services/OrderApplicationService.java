package com.kvn.ddd.application.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kvn.ddd.application.dto.OrderDto;
import com.kvn.ddd.application.dto.OrderItemDto;
import com.kvn.ddd.application.dto.TaxComponentDto;
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

@Service
public class OrderApplicationService {
    
    private final OrderRepository orderRepository;

    public OrderApplicationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addItemToOrder(Long orderId, Long productId, Integer quantity, String skuId, Double unitPrice, Double sgstRate, Double cgstRate) {
        OrderId orderIdVO = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdVO)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Amount unitPriceAmount = new Amount(unitPrice);
        List<TaxComponent> taxComponents = Arrays.asList(
            new TaxComponent("SGST", new TaxRate(sgstRate), unitPriceAmount),
            new TaxComponent("CGST", new TaxRate(cgstRate), unitPriceAmount)
        );

        OrderItem orderItem = new OrderItem(
            new ProductId(productId),
            new Quantity(quantity),
            skuId,
            unitPriceAmount,
            taxComponents
        );

        order.addOrderItem(orderItem);
        orderRepository.save(order);
    }

    public Order createOrder(Long orderId, Long userId) {
        OrderId orderIdVO = new OrderId(orderId);
        UserId userIdVO = new UserId(userId);
        Order order = new Order(orderIdVO, userIdVO);
        return orderRepository.save(order);
    }

    public OrderDto getOrder(Long orderId) {
        OrderId orderIdVO = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdVO)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
            .map(this::mapToOrderItemDto)
            .collect(Collectors.toList());

        return new OrderDto(
            orderId,
            order.getCreatedDate(),
            order.getUpdatedDate(),
            order.getItemCount(),
            order.getTotalNumberOfItems(),
            order.calculateSubtotal().getValue(),
            order.calculateTotalGST().getValue(),
            order.calculateTotal().getValue(),
            itemDtos
        );
    }

    public OrderItemDto getOrderItem(Long orderId, int itemIndex) {
        OrderId orderIdVO = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdVO)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (itemIndex >= order.getOrderItems().size()) {
            throw new IllegalArgumentException("Order item not found");
        }

        OrderItem orderItem = order.getOrderItems().get(itemIndex);
        return mapToOrderItemDto(orderItem);
    }

    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        List<TaxComponentDto> taxComponentDtos = orderItem.getTaxComponents().stream()
            .map(tc -> new TaxComponentDto(tc.getName(), tc.getRate().getPercentage(), tc.getAmount().getValue()))
            .collect(Collectors.toList());

        return new OrderItemDto(
            orderItem.getProductId().getValue(),
            orderItem.getQuantity().getValue(),
            orderItem.getSkuId(),
            orderItem.getUnitPrice().getValue(),
            orderItem.calculateSubtotal().getValue(),
            orderItem.calculateTotalTax().getValue(),
            orderItem.calculateTotal().getValue(),
            taxComponentDtos
        );
    }
}