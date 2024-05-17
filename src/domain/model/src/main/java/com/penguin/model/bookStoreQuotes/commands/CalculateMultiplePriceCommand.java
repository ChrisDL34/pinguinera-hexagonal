package com.penguin.model.bookStoreQuotes.commands;

import com.penguin.model.bookStoreQuotes.entityprimitive.ProductIdAndQuantity;
import com.penguin.model.generic.Command;

import java.time.LocalDate;
import java.util.List;

public class CalculateMultiplePriceCommand extends Command {

    private String bookStoreQuotesId;

    private List<ProductIdAndQuantity> productsIdsQuantity;

    private LocalDate registrationDate;

    public CalculateMultiplePriceCommand(String bookStoreQuotesId, List<ProductIdAndQuantity> productsIdsQuantity, LocalDate registrationDate) {
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.productsIdsQuantity = productsIdsQuantity;
        this.registrationDate = registrationDate;
    }

    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public List<ProductIdAndQuantity> getProductsIdsQuantity() {
        return productsIdsQuantity;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}
