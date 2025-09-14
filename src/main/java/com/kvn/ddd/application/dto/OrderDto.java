package com.kvn.ddd.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long orderId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int itemCount;
    private int totalQuantity;
    private BigDecimal subtotal;
    private BigDecimal totalGST;
    private BigDecimal total;
    private List<OrderItemDto> items;

    public OrderDto(Long orderId, LocalDateTime createdDate, LocalDateTime updatedDate, int itemCount, 
                   int totalQuantity, BigDecimal subtotal, BigDecimal totalGST, BigDecimal total, List<OrderItemDto> items) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.itemCount = itemCount;
        this.totalQuantity = totalQuantity;
        this.subtotal = subtotal;
        this.totalGST = totalGST;
        this.total = total;
        this.items = items;
    }

    public Long getOrderId() { return orderId; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public int getItemCount() { return itemCount; }
    public int getTotalQuantity() { return totalQuantity; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTotalGST() { return totalGST; }
    public BigDecimal getTotal() { return total; }
    public List<OrderItemDto> getItems() { return items; }
}