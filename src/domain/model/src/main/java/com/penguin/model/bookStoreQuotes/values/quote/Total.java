package com.penguin.model.bookStoreQuotes.values.quote;

import com.penguin.model.generic.ValueObject;

public class Total implements ValueObject<Double>{

    private  Double total;

    public Total(Double total) {
        if (total == null) {
            throw new IllegalArgumentException("Total cannot be null");
        }
        if (total < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        this.total = total;
    }

    public Total() {
    }

    @Override
    public Double value() {
        return total;
    }
}
