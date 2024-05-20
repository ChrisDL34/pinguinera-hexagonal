package com.penguin.model.bookStoreQuotes.values.copy;

import com.penguin.model.generic.ValueObject;

public class TotalPriceWithDiscount implements ValueObject<Double> {
    private  Double value;

    public TotalPriceWithDiscount(Double value) {
        this.value = value;
    }

    public TotalPriceWithDiscount() {
    }

    @Override
    public Double value() {
        return value;
    }
}