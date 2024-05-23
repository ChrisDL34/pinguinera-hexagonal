package com.penguin.model.bookStoreQuotes.values.BookStoreQuotes;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.CopyIdAndQuantity;

import java.util.List;

public class GroupPriceResult {
    private List<CopyIdAndQuantity> copiesIdsQuantity;
    private double totalPrice;
    private double totalPriceWithDiscount;

    public GroupPriceResult(List<CopyIdAndQuantity> copiesIdsQuantity, double totalPrice, double totalPriceWithDiscount) {
        this.copiesIdsQuantity = copiesIdsQuantity;
        this.totalPrice = totalPrice;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public GroupPriceResult() {

    }



    public List<CopyIdAndQuantity> getCopiesIdsQuantity() {
        return copiesIdsQuantity;
    }

    public void setCopiesIdsQuantity(List<CopyIdAndQuantity> copiesIdsQuantity) {
        this.copiesIdsQuantity = copiesIdsQuantity;
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