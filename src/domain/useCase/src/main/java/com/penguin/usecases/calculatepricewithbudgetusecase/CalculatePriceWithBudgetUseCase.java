package com.penguin.usecases.calculatepricewithbudgetusecase;

import com.penguin.gateways.DomainEventRepository;
import com.penguin.generic.CommandUseCase;
import com.penguin.model.bookStoreQuotes.BookStoreQuotes;
import com.penguin.model.bookStoreQuotes.commands.CalculatePriceWithBudgetCommand;
import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.events.CalculatedPriceWithBudget;
import com.penguin.model.bookStoreQuotes.events.CalculatedPriceWithBudget.BookDetails;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.bookStoreQuotes.values.responses.SimplifiedCalculatedPriceWithBudget;
import com.penguin.model.generic.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CalculatePriceWithBudgetUseCase extends CommandUseCase<CalculatePriceWithBudgetCommand> {
    private final DomainEventRepository repository;

    @Autowired
    public CalculatePriceWithBudgetUseCase(DomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CalculatePriceWithBudgetCommand> calculatePriceWithBudgetCommandMono) {
        return calculatePriceWithBudgetCommandMono.flatMapMany(command -> repository.findById(command.getBookStoreQuotesId())
                .collectList()
                .flatMapMany(events -> {
                    BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuotesId());
                    BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events);

                    bookStoreQuotes.calculatePriceWithBudget(
                            command.getCopiesIds(),
                            command.getRegistrationDate(),
                            BigDecimal.valueOf(command.getBudget()),
                            events
                    );

                    return Flux.fromIterable(bookStoreQuotes.getUncommittedChanges())
                            .flatMap(repository::saveEvent);
                }));
    }

    public Mono<SimplifiedCalculatedPriceWithBudget> calculateAndSimplify(Mono<CalculatePriceWithBudgetCommand> calculatePriceWithBudgetCommandMono) {
        return calculatePriceWithBudgetCommandMono.flatMap(command -> repository.findById(command.getBookStoreQuotesId())
                .collectList()
                .map(events -> {
                    BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuotesId());
                    BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events);

                    bookStoreQuotes.calculatePriceWithBudget(
                            command.getCopiesIds(),
                            command.getRegistrationDate(),
                            BigDecimal.valueOf(command.getBudget()),
                            events
                    );

                    return toSimplifiedCalculatedPriceWithBudget(bookStoreQuotes);
                }));
    }

    private SimplifiedCalculatedPriceWithBudget toSimplifiedCalculatedPriceWithBudget(BookStoreQuotes bookStoreQuotes) {
        SimplifiedCalculatedPriceWithBudget simplified = new SimplifiedCalculatedPriceWithBudget();
        simplified.setTotalPriceWithDiscount(bookStoreQuotes.getTotalPriceWithDiscount().value());
        simplified.setBookDetails(bookStoreQuotes.getBookDetails());
        return simplified;
    }
}
