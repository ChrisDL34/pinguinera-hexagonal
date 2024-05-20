package com.penguin.usecases.calculateproductspricegroupsuseCase;

import com.penguin.gateways.DomainEventRepository;
import com.penguin.generic.CommandUseCase;
import com.penguin.model.bookStoreQuotes.BookStoreQuotes;
import com.penguin.model.bookStoreQuotes.commands.CalculateMultiplePriceGroupsCommand;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePriceGroups;
import com.penguin.model.bookStoreQuotes.factory.CopyFactory;
import com.penguin.model.bookStoreQuotes.factory.CopyFactoryImpl;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.bookStoreQuotes.values.responses.SimplifiedCalculatedMultiplePriceGroups;
import com.penguin.model.generic.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CalculateProductsPriceGroupsUseCase extends CommandUseCase<CalculateMultiplePriceGroupsCommand> {
    private final DomainEventRepository repository;
    private final CopyFactory copyFactory;

    @Autowired
    public CalculateProductsPriceGroupsUseCase(DomainEventRepository repository) {
        this.repository = repository;
        this.copyFactory = new CopyFactoryImpl();
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CalculateMultiplePriceGroupsCommand> calculateMultiplePriceGroupsCommandMono) {
        return calculateMultiplePriceGroupsCommandMono.flatMapMany(command -> repository.findById(command.getBookStoreQuotesId())
                .collectList()
                .flatMapMany(events -> {
                    BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuotesId());
                    BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events, copyFactory);

                    CalculatedMultiplePriceGroups calculatedMultiplePriceGroups = bookStoreQuotes.calculateProductPriceGroups(
                            command.getCopiesIdsQuantityGroups(),
                            command.getRegistrationDate(),
                            events
                    );

                    return repository.saveEvent(calculatedMultiplePriceGroups)
                            .thenMany(Flux.fromIterable(bookStoreQuotes.getUncommittedChanges()));
                }));
    }
    public Mono<SimplifiedCalculatedMultiplePriceGroups> calculateAndSimplify(Mono<CalculateMultiplePriceGroupsCommand> calculateMultiplePriceGroupsCommandMono) {
        return apply(calculateMultiplePriceGroupsCommandMono)
                .filter(event -> event instanceof CalculatedMultiplePriceGroups)
                .map(event -> (CalculatedMultiplePriceGroups) event)
                .map(this::toSimplifiedCalculatedMultiplePriceGroups)
                .next();
    }

    private SimplifiedCalculatedMultiplePriceGroups toSimplifiedCalculatedMultiplePriceGroups(CalculatedMultiplePriceGroups event) {
        SimplifiedCalculatedMultiplePriceGroups simplified = new SimplifiedCalculatedMultiplePriceGroups();
        simplified.setGroupPriceResults(event.getGroupPriceResults());
        simplified.setRegistrationDate(event.getRegistrationDate());
        return simplified;
    }


}