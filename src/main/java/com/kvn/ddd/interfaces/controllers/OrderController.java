package com.kvn.ddd.interfaces.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kvn.ddd.application.dto.OrderDto;
import com.kvn.ddd.application.dto.OrderItemDto;
import com.kvn.ddd.application.services.OrderApplicationService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<String> addOrderItem(@PathVariable Long orderId, @RequestBody AddOrderItemRequest request) {
        orderApplicationService.addItemToOrder(orderId, request.getProductId(), request.getQuantity(), 
            request.getSkuId(), request.getUnitPrice(), request.getSgstRate(), request.getCgstRate());
        return ResponseEntity.ok("Order item added successfully");
    }

    @PostMapping("/{orderId}/user/{userId}")
    public ResponseEntity<String> createOrder(@PathVariable Long orderId, @PathVariable Long userId) {
        orderApplicationService.createOrder(orderId, userId);
        return ResponseEntity.ok("Order created successfully");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto order = orderApplicationService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}/items/{itemIndex}")
    public ResponseEntity<OrderItemDto> getOrderItem(@PathVariable Long orderId, @PathVariable int itemIndex) {
        OrderItemDto orderItem = orderApplicationService.getOrderItem(orderId, itemIndex);
        return ResponseEntity.ok(orderItem);
    }

    public static class AddOrderItemRequest {
        private Long productId;
        private Integer quantity;
        private String skuId;
        private Double unitPrice;
        private Double sgstRate;
        private Double cgstRate;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public String getSkuId() { return skuId; }
        public void setSkuId(String skuId) { this.skuId = skuId; }
        
        public Double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
        
        public Double getSgstRate() { return sgstRate; }
        public void setSgstRate(Double sgstRate) { this.sgstRate = sgstRate; }
        
        public Double getCgstRate() { return cgstRate; }
        public void setCgstRate(Double cgstRate) { this.cgstRate = cgstRate; }
    }
}