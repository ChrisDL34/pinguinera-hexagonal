package com.penguin.calculateproductspriceusecase;

import com.penguin.gateways.DomainEventRepository;
import com.penguin.generic.CommandUseCase;
import com.penguin.model.bookStoreQuotes.BookStoreQuotes;
import com.penguin.model.bookStoreQuotes.commands.CalculateMultiplePriceCommand;
import com.penguin.model.bookStoreQuotes.commands.SaveBookCommand;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.generic.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class CalculateProductsPriceUseCase extends CommandUseCase<CalculateMultiplePriceCommand> {
    private final DomainEventRepository repository;

    public CalculateProductsPriceUseCase(DomainEventRepository domainEventRepository) {
        this.repository = domainEventRepository;
    }


    @Override
    public Flux<DomainEvent> apply(Mono<CalculateMultiplePriceCommand> calculateMultiplePriceCommandMono) {
        return calculateMultiplePriceCommandMono.flatMapMany(command -> repository.findById(command.getBookStoreQuotesId())
                .collectList()
                .flatMapIterable(events -> {
                    BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuotesId());
                        BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events);

                        Objects.requireNonNull(command.getBookStoreQuotesId(), "Aggregate Root cannot be null here");

                        bookStoreQuotes.calculateProductPrice(
                                command.getBookStoreQuotesId(),
                                command.getProductsIdsQuantity(),
                                command.getRegistrationDate()
                        );

                        return bookStoreQuotes.getUncommittedChanges();
                })
                .map(event -> event)
                .flatMap(repository::saveEvent)
        );
    }
}
