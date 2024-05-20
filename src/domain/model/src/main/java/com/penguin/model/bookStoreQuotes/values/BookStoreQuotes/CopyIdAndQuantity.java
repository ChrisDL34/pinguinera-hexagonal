package com.penguin.model.bookStoreQuotes.values.BookStoreQuotes;



import com.penguin.model.bookStoreQuotes.values.copy.UnitPrice;

import java.util.List;
import java.util.UUID;

public class CopyIdAndQuantity {
    private UUID id;
    private int quantity;
    private String title;
    private String author;

    private String copyType;
    private List<UnitPrice> unitPrices;
    private double totalPrice;


    public CopyIdAndQuantity() {
    }

    public CopyIdAndQuantity(UUID id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public CopyIdAndQuantity(UUID id, int quantity, String title, String author) {
        this.id = id;
        this.quantity = quantity;
        this.title = title;
        this.author = author;
    }

    public CopyIdAndQuantity(UUID id, int quantity, String title, String author, String copyType) {
        this.id = id;
        this.quantity = quantity;
        this.title = title;
        this.author = author;
        this.copyType = copyType;
    }

    public CopyIdAndQuantity(UUID id, int quantity, String title, String author, String copyType, List<UnitPrice> unitPrices, double totalPrice) {
        this.id = id;
        this.quantity = quantity;
        this.title = title;
        this.author = author;
        this.copyType = copyType;
        this.unitPrices = unitPrices;
        this.totalPrice = totalPrice;
    }

    public List<UnitPrice> getUnitPrices() {
        return unitPrices;
    }

    public void setUnitPrices(List<UnitPrice> unitPrices) {
        this.unitPrices = unitPrices;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCopyType() {
        return copyType;
    }

    public void setCopyType(String copyType) {
        this.copyType = copyType;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}