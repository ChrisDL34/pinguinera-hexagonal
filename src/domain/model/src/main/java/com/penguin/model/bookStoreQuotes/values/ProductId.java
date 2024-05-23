package com.penguin.model.bookStoreQuotes.values;

import com.penguin.model.generic.ValueObject;


public class ProductId implements ValueObject<String> {

    private final String productId;

    public ProductId(String productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.productId = productId;
    }

    public static ProductId of(String productId) {
        return new ProductId(productId);
    }

    @Override
    public String value() {
        return productId;
    }
}
