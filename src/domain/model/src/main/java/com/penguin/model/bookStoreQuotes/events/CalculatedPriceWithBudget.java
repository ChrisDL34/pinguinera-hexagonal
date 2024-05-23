package com.penguin.model.bookStoreQuotes.events;

import com.penguin.model.bookStoreQuotes.events.enums.TypeEvent;
import com.penguin.model.generic.DomainEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CalculatedPriceWithBudget extends DomainEvent {
    private String bookStoreQuotesId;
    private List<String> copiesIds;
    private LocalDate registrationDate;
    private double budget;
    private Map<UUID, Integer> recommendations;
    private double totalPriceWithDiscount;
    private List<BookDetails> bookDetails;

    public CalculatedPriceWithBudget() {
    }

    public CalculatedPriceWithBudget(String bookStoreQuotesId, List<String> copiesIds,
                                     LocalDate registrationDate, double budget, Map<UUID, Integer> recommendations,
                                     double totalPriceWithDiscount, List<BookDetails> bookDetails) {
        super(TypeEvent.CalculatedPriceWithBudget.toString());
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.copiesIds = copiesIds;
        this.registrationDate = registrationDate;
        this.budget = budget;
        this.recommendations = recommendations;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
        this.bookDetails = bookDetails;
    }



    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public List<String> getCopiesIds() {
        return copiesIds;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public double getBudget() {
        return budget;
    }

    public Map<UUID, Integer> getRecommendations() {
        return recommendations;
    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public List<BookDetails> getBookDetails() {
        return bookDetails;
    }










    public static class BookDetails {
        private UUID id;
        private String title;
        private String type;
        private String author;
        private int recommendation;

        public BookDetails(UUID id, String title, String type, String author) {
            this.id = id;
            this.title = title;
            this.type = type;
            this.author = author;
        }

        public BookDetails(UUID id, String title, String type, String author, int recommendation) {
            this.id = id;
            this.title = title;
            this.type = type;
            this.author = author;
            this.recommendation = recommendation;
        }

        public int getRecommendation() {
            return recommendation;
        }

        public void setRecommendation(int recommendation) {
            this.recommendation = recommendation;
        }

        public BookDetails() {
        }

        public UUID getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public String getAuthor() {
            return author;
        }
    }
}
