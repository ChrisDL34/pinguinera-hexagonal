package com.penguin.model.bookStoreQuotes.events;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.CopyIdAndQuantity;
import com.penguin.model.bookStoreQuotes.events.enums.TypeEvent;
import com.penguin.model.generic.DomainEvent;

import java.time.LocalDate;
import java.util.List;

public class CalculatedMultiplePrice extends DomainEvent {
    private String bookStoreQuotesId;
    private List<CopyIdAndQuantity> copiesIdsQuantity;
    private LocalDate registrationDate;
    private double totalPrice;
    private double totalPriceWithDiscount;
    private double discountPercentage;
    private double discountedPrice;


    public CalculatedMultiplePrice() {

    }
    public CalculatedMultiplePrice(String bookStoreQuotesId, List<CopyIdAndQuantity> copiesIdsQuantity,
                                   LocalDate registrationDate, double totalPrice, double totalPriceWithDiscount) {
        super(TypeEvent.CalculatedMultiplePrice.toString());
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.copiesIdsQuantity = copiesIdsQuantity;
        this.registrationDate = registrationDate;
        this.totalPrice = totalPrice;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public CalculatedMultiplePrice(String bookStoreQuotesId, List<CopyIdAndQuantity> copiesIdsQuantity,
                                   LocalDate registrationDate, double totalPrice, double totalPriceWithDiscount,
                                   double discountPercentage, double discountedPrice) {
        super(TypeEvent.CalculatedMultiplePrice.toString());
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.copiesIdsQuantity = copiesIdsQuantity;
        this.registrationDate = registrationDate;
        this.totalPrice = totalPrice;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
        this.discountPercentage = discountPercentage;
        this.discountedPrice = discountedPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }




    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public void setBookStoreQuotesId(String bookStoreQuotesId) {
        this.bookStoreQuotesId = bookStoreQuotesId;
    }

    public List<CopyIdAndQuantity> getCopiesIdsQuantity() {
        return copiesIdsQuantity;
    }

    public void setCopiesIdsQuantity(List<CopyIdAndQuantity> copiesIdsQuantity) {
        this.copiesIdsQuantity = copiesIdsQuantity;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(double totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }
}
