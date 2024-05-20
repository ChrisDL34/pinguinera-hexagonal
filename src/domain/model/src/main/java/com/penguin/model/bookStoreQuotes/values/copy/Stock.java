package com.penguin.model.bookStoreQuotes.values.copy;

import com.penguin.model.generic.ValueObject;

public class Stock implements ValueObject<Integer> {

    private  int stock;

    public Stock(int stock) {
        if( stock > 0 ){
            this.stock = stock;
        }else throw new IllegalArgumentException("stock must be greater than 0");
    }

    public Stock() {
    }

    @Override
    public Integer value() {
        return this.stock;
    }
}
