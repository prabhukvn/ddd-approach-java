package com.kvn.ddd.application.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemDto {
    private Long productId;
    private Integer quantity;
    private String skuId;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private BigDecimal totalTax;
    private BigDecimal total;
    private List<TaxComponentDto> taxComponents;

    public OrderItemDto(Long productId, Integer quantity, String skuId, BigDecimal unitPrice, 
                       BigDecimal subtotal, BigDecimal totalTax, BigDecimal total, List<TaxComponentDto> taxComponents) {
        this.productId = productId;
        this.quantity = quantity;
        this.skuId = skuId;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.totalTax = totalTax;
        this.total = total;
        this.taxComponents = taxComponents;
    }

    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public String getSkuId() { return skuId; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTotalTax() { return totalTax; }
    public BigDecimal getTotal() { return total; }
    public List<TaxComponentDto> getTaxComponents() { return taxComponents; }
}