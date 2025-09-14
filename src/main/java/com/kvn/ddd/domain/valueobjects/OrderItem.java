package com.kvn.ddd.domain.valueobjects;

import java.util.List;
import java.util.Objects;

public class OrderItem {
    private final ProductId productId;
    private final Quantity quantity;
    private final String skuId;
    private final Amount unitPrice;
    private final List<TaxComponent> taxComponents;

    public OrderItem(ProductId productId, Quantity quantity, String skuId, Amount unitPrice, List<TaxComponent> taxComponents) {
        this.productId = Objects.requireNonNull(productId, "ProductId cannot be null");
        this.quantity = Objects.requireNonNull(quantity, "Quantity cannot be null");
        this.skuId = Objects.requireNonNull(skuId, "SkuId cannot be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit price cannot be null");
        this.taxComponents = Objects.requireNonNull(taxComponents, "Tax components cannot be null");
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public String getSkuId() {
        return skuId;
    }

    public Amount getUnitPrice() {
        return unitPrice;
    }

    public List<TaxComponent> getTaxComponents() {
        return taxComponents;
    }

    public Amount calculateSubtotal() {
        return unitPrice.multiply(quantity.getValue());
    }

    public Amount calculateTotalTax() {
        return taxComponents.stream()
                .map(TaxComponent::getAmount)
                .reduce(new Amount(0), Amount::add)
                .multiply(quantity.getValue());
    }

    public Amount calculateTotal() {
        return calculateSubtotal().add(calculateTotalTax());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productId, orderItem.productId) &&
               Objects.equals(quantity, orderItem.quantity) &&
               Objects.equals(skuId, orderItem.skuId) &&
               Objects.equals(unitPrice, orderItem.unitPrice) &&
               Objects.equals(taxComponents, orderItem.taxComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, skuId, unitPrice, taxComponents);
    }
}