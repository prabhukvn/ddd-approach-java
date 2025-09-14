package com.kvn.ddd.domain.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

public class Amount {
    private final BigDecimal value;

    public Amount(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be null or negative");
        }
        this.value = value;
    }

    public Amount(double value) {
        this(BigDecimal.valueOf(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    public Amount multiply(int quantity) {
        return new Amount(value.multiply(BigDecimal.valueOf(quantity)));
    }

    public Amount add(Amount other) {
        return new Amount(value.add(other.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Objects.equals(value, amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}