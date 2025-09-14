package com.kvn.ddd.domain.valueobjects;

import java.util.Objects;

public class TaxComponent {
    private final String name;
    private final TaxRate rate;
    private final Amount amount;

    public TaxComponent(String name, TaxRate rate, Amount baseAmount) {
        this.name = Objects.requireNonNull(name, "Tax component name cannot be null");
        this.rate = Objects.requireNonNull(rate, "Tax rate cannot be null");
        this.amount = rate.calculateTax(baseAmount);
    }

    public String getName() {
        return name;
    }

    public TaxRate getRate() {
        return rate;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxComponent that = (TaxComponent) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(rate, that.rate) &&
               Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate, amount);
    }
}