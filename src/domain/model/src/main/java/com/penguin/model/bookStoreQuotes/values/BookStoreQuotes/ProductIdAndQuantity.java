package com.penguin.model.bookStoreQuotes.values.BookStoreQuotes;

public class ProductIdAndQuantity {

    private String id;

    private Integer quantity;


    public ProductIdAndQuantity() {
    }
    public ProductIdAndQuantity(String id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
