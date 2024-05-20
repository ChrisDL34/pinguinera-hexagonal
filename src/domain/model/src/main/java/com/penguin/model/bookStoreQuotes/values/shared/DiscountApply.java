package com.penguin.model.bookStoreQuotes.values.shared;

import com.penguin.model.generic.ValueObject;

public class DiscountApply implements ValueObject<Double> {

    private  Double discount;

    public DiscountApply(Double discount) {
        if (discount == null || discount < 0) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.discount = discount;
    }

    public DiscountApply() {
    }

    public static DiscountApply of(Double discount){
        return new DiscountApply(discount);
    }

    @Override
    public Double value() {
        return discount;
    }
}
