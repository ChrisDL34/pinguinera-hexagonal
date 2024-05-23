package com.penguin.model.bookStoreQuotes.values.copy;

import com.penguin.model.generic.ValueObject;

public class Price implements ValueObject<Double> {

    private  double price;

    public Price(double price) {
        if(price > 0){
            this.price = price;
        }else throw new IllegalArgumentException("price must be greater than 0");
    }

    public Price() {
    }

    @Override
    public Double value() {
        return this.price;
    }
}
