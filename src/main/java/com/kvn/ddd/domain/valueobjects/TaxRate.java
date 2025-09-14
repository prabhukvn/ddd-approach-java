package com.kvn.ddd.domain.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

public class TaxRate {
    private final BigDecimal percentage;

    public TaxRate(BigDecimal percentage) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    public TaxRate(double percentage) {
        this(BigDecimal.valueOf(percentage));
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public Amount calculateTax(Amount baseAmount) {
        BigDecimal taxAmount = baseAmount.getValue().multiply(percentage).divide(BigDecimal.valueOf(100));
        return new Amount(taxAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRate taxRate = (TaxRate) o;
        return Objects.equals(percentage, taxRate.percentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage);
    }
}