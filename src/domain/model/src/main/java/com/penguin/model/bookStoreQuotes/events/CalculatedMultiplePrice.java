package com.penguin.model.bookStoreQuotes.events;

import com.penguin.model.bookStoreQuotes.entityprimitive.ProductIdAndQuantity;
import com.penguin.model.generic.DomainEvent;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CalculatedMultiplePrice extends DomainEvent {
    private String bookStoreQuotesId;

    private List<ProductIdAndQuantity> productsIdsQuantity;

    private LocalDate registrationDate;

    public CalculatedMultiplePrice(String bookStoreQuotesId, List<ProductIdAndQuantity> productsIdsQuantity, LocalDate registrationDate) {
        super(TypeEvent.CALCULATED_MULTIPLE_PRICE.name());
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.productsIdsQuantity = productsIdsQuantity;
        this.registrationDate = registrationDate;
    }

    public CalculatedMultiplePrice(Instant when, UUID uuid, String type, String aggregateRootId, String aggregate, Long versionType, String bookStoreQuotesId, List<ProductIdAndQuantity> productsIdsQuantity, LocalDate registrationDate) {
        super(when, uuid, type, aggregateRootId, aggregate, versionType);
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.productsIdsQuantity = productsIdsQuantity;
        this.registrationDate = registrationDate;
    }

    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public void setBookStoreQuotesId(String bookStoreQuotesId) {
        this.bookStoreQuotesId = bookStoreQuotesId;
    }

    public List<ProductIdAndQuantity> getProductsIdsQuantity() {
        return productsIdsQuantity;
    }

    public void setProductsIdsQuantity(List<ProductIdAndQuantity> productsIdsQuantity) {
        this.productsIdsQuantity = productsIdsQuantity;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
