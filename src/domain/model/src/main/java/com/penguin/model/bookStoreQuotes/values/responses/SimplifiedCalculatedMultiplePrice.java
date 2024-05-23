package com.penguin.model.bookStoreQuotes.values.responses;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.CopyIdAndQuantity;

import java.time.LocalDate;
import java.util.List;



public class SimplifiedCalculatedMultiplePrice {
   // private String bookStoreQuotesId;
    private List<CopyIdAndQuantity> copiesIdsQuantity;
    private LocalDate registrationDate;
    private double totalPrice;
    private double totalPriceWithDiscount;
    private double discountPercentage;
    private double discountedPrice;

    public SimplifiedCalculatedMultiplePrice( List<CopyIdAndQuantity> copiesIdsQuantity, LocalDate registrationDate, double totalPrice, double totalPriceWithDiscount, double discountPercentage, double discountedPrice) {

        this.copiesIdsQuantity = copiesIdsQuantity;
        this.registrationDate = registrationDate;
        this.totalPrice = totalPrice;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
        this.discountPercentage = discountPercentage;
        this.discountedPrice = discountedPrice;
    }

    public SimplifiedCalculatedMultiplePrice() {
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

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
