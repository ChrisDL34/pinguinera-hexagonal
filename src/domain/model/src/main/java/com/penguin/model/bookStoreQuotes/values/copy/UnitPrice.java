package com.penguin.model.bookStoreQuotes.values.copy;

public class UnitPrice {
    private int unit;
    private double price;

    public UnitPrice(int unit, double price) {
        this.unit = unit;
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public UnitPrice() {
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
