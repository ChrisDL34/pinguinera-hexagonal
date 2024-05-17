package com.penguin.model.bookStoreQuotes;

import com.penguin.model.bookStoreQuotes.entity.Copy;
import com.penguin.model.bookStoreQuotes.entity.Quote;
import com.penguin.model.bookStoreQuotes.entityprimitive.ProductIdAndQuantity;
import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.events.BookStoreQuotesCreated;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePrice;
import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.*;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.generic.AggregateRoot;
import com.penguin.model.generic.DomainEvent;

import java.time.LocalDate;
import java.util.List;

public class BookStoreQuotes extends AggregateRoot<BookStoreQuoteId> {

    protected Copy copy;
    protected TotalPrice totalPrice;
    protected TotalDiscount totalDiscount;
    protected TotalIncrement totalIncrement;
    protected CustomerRegistrationDate customerRegistrationDate;
    protected Seniority seniority;
    protected List<Copy> copies;
    protected List<Quote> quotes;
    protected Copy result;

    private BookStoreQuotes(BookStoreQuoteId bookStoreQuoteId) {
        super(bookStoreQuoteId);
    }

    public BookStoreQuotes(Title title,
                           Author author,
                           Stock stock,
                           PublicationYear publicationYear,
                           Price price,
                           Type type) {
        super(new BookStoreQuoteId());
        subscribe(new BookStoreQuotesEventChange(this));
        appendChange(new BookSaved(
                title.value(),
                author.value(),
                stock.value(),
                publicationYear.value(),
                price.value(), type.value())).apply();
    }

    public void addCopy(Title title,
                        Author author,
                        Stock stock,
                        PublicationYear publicationYear,
                        Price price,
                        Type type) {
        appendChange(new BookSaved(
                title.value(),
                author.value(),
                stock.value(),
                publicationYear.value(),
                price.value(), type.value())).apply();
    }

    public Copy getResult() {
        return result;
    }

    public void setResult(Copy result) {
        this.result = result;
    }

    public static BookStoreQuotes from(BookStoreQuoteId bookStoreQuoteId, List<DomainEvent> events) {
        var bookStoreQuotes = new BookStoreQuotes(bookStoreQuoteId);
        events.forEach(bookStoreQuotes::applyEvent);
        return bookStoreQuotes;
    }


    public void calculateProductPrice(String bookStoreQuotesId, List<ProductIdAndQuantity> productsIdsQuantity, LocalDate registrationDate){
        appendChange(new CalculatedMultiplePrice(bookStoreQuotesId, productsIdsQuantity, registrationDate)).apply();
    }
}
