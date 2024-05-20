package com.penguin.model.bookStoreQuotes.values.responses;

import com.penguin.model.bookStoreQuotes.events.CalculatedPriceWithBudget.BookDetails;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimplifiedCalculatedPriceWithBudget {
   // private Map<UUID, Integer> recommendations;
    private double totalPriceWithDiscount;
    private List<BookDetails> bookDetails;



    public SimplifiedCalculatedPriceWithBudget() {
    }

    public SimplifiedCalculatedPriceWithBudget(Map<UUID, Integer> recommendations, double totalPriceWithDiscount, List<BookDetails> bookDetails) {
       // this.recommendations = recommendations;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
        this.bookDetails = bookDetails;
    }

//    public Map<UUID, Integer> getRecommendations() {
//        return recommendations;
//    }
//
//    public void setRecommendations(Map<UUID, Integer> recommendations) {
//        this.recommendations = recommendations;
//    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(double totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public List<BookDetails> getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(List<BookDetails> bookDetails) {
        this.bookDetails = bookDetails;
    }
}