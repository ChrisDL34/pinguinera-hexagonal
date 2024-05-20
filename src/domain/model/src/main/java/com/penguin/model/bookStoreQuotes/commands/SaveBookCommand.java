package com.penguin.model.bookStoreQuotes.commands;

import com.penguin.model.generic.Command;

public class SaveBookCommand extends Command {

    private String bookStoreQuoteId;
    private String title;
    private String author;
    private Integer stock;
    private Integer publicationYear;
    private Double price;
    private String type;


    public SaveBookCommand() {
    }

    public String getBookStoreQuoteId() {
        return bookStoreQuoteId;
    }

    public void setBookStoreQuoteId(String bookStoreQuoteId) {
        this.bookStoreQuoteId = bookStoreQuoteId;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
