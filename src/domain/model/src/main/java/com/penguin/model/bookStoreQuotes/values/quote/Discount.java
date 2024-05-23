package com.penguin.model.bookStoreQuotes.values.quote;

import com.penguin.model.generic.ValueObject;

public class Discount implements ValueObject<Double> {

    private  Double discount;

    public Discount(Double discount) {
        if (discount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }
        if (discount < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        this.discount = discount;
    }

    public Discount() {
    }

    @Override
    public Double value() {
        return discount;
    }
}
