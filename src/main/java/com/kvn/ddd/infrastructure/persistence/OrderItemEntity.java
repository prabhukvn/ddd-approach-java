package com.kvn.ddd.infrastructure.persistence;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemEntity {
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "sku_id")
    private String skuId;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "sgst_rate")
    private BigDecimal sgstRate;
    
    @Column(name = "cgst_rate")
    private BigDecimal cgstRate;

    public OrderItemEntity() {}

    public OrderItemEntity(Long productId, Integer quantity, String skuId, BigDecimal unitPrice, BigDecimal sgstRate, BigDecimal cgstRate) {
        this.productId = productId;
        this.quantity = quantity;
        this.skuId = skuId;
        this.unitPrice = unitPrice;
        this.sgstRate = sgstRate;
        this.cgstRate = cgstRate;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getSkuId() { return skuId; }
    public void setSkuId(String skuId) { this.skuId = skuId; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    
    public BigDecimal getSgstRate() { return sgstRate; }
    public void setSgstRate(BigDecimal sgstRate) { this.sgstRate = sgstRate; }
    
    public BigDecimal getCgstRate() { return cgstRate; }
    public void setCgstRate(BigDecimal cgstRate) { this.cgstRate = cgstRate; }
}