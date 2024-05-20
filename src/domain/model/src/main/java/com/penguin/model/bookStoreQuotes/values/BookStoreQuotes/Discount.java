package com.penguin.model.bookStoreQuotes.values.BookStoreQuotes;

public class Discount {
    private double percentage;
    private double discountedPrice;


    public Discount() {
    }
    public Discount(double percentage, double discountedPrice) {
        this.percentage = percentage;
        this.discountedPrice = discountedPrice;
    }



    public double getPercentage() {
        return percentage;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }
}