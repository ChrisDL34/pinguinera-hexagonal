package com.penguin.usecases.saveCopyUseCase;

import com.penguin.gateways.DomainEventRepository;
import com.penguin.generic.CommandUseCase;
import com.penguin.model.bookStoreQuotes.BookStoreQuotes;
import com.penguin.model.bookStoreQuotes.commands.SaveBookCommand;
import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.factory.CopyFactory;
import com.penguin.model.bookStoreQuotes.factory.CopyFactoryImpl;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.bookStoreQuotes.values.responses.SimplifiedBookResponse;
import com.penguin.model.generic.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaveCopyUseCase extends CommandUseCase<SaveBookCommand> {

    private final DomainEventRepository repository;
    private final CopyFactory copyFactory;

    @Autowired
    public SaveCopyUseCase(DomainEventRepository domainEventRepository) {
        this.repository = domainEventRepository;
        this.copyFactory = new CopyFactoryImpl();
    }

    @Override
    public Flux<DomainEvent> apply(Mono<SaveBookCommand> commandMono) {
        return commandMono.flatMapMany(command ->
                repository.findById(command.getBookStoreQuoteId())
                        .collectList()
                        .flatMapMany(events -> {
                            BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuoteId());
                            BookStoreQuotes bookStoreQuotes;
                            if (events.isEmpty()) {
                                bookStoreQuotes = new BookStoreQuotes(
                                        new Title(command.getTitle()),
                                        new Author(command.getAuthor()),
                                        new Stock(command.getStock()),
                                        new PublicationYear(command.getPublicationYear()),
                                        new Price(command.getPrice()),
                                        new Type(command.getType())
                                );
                            } else {
                                bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events, copyFactory);
                                bookStoreQuotes.addCopy(
                                        new Title(command.getTitle()),
                                        new Author(command.getAuthor()),
                                        new Stock(command.getStock()),
                                        new PublicationYear(command.getPublicationYear()),
                                        new Price(command.getPrice()),
                                        new Type(command.getType())
                                );
                            }
                            return Flux.fromIterable(bookStoreQuotes.getUncommittedChanges())
                                    .flatMap(repository::saveEvent);
                        })
        );
    }

    public Mono<SimplifiedBookResponse> mapToSimplifiedResponse(Mono<SaveBookCommand> commandMono) {
        return this.apply(commandMono)
                .collectList()
                .map(events -> {
                    BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(
                            BookStoreQuoteId.of(commandMono.block().getBookStoreQuoteId()), events, copyFactory);
                    return events.stream()
                            .filter(event -> event instanceof BookSaved)
                            .map(event -> (BookSaved) event)
                            .map(event -> new SimplifiedBookResponse(
                                    event.getTitle(),
                                    event.getAuthor(),
                                    event.getStock(),
                                    event.getPublicationYear(),
                                    event.getPrice(),
                                    event.getCopyType()
                            ))
                            .findFirst()
                            .orElse(null);
                });
    }
}
