package com.penguin.saveCopyUseCase;

import com.penguin.gateways.DomainEventRepository;
import com.penguin.generic.CommandUseCase;
import com.penguin.model.bookStoreQuotes.BookStoreQuotes;
import com.penguin.model.bookStoreQuotes.commands.SaveBookCommand;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.generic.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SaveCopyUseCase extends CommandUseCase<SaveBookCommand> {

    private final DomainEventRepository repository;

    @Autowired
    public SaveCopyUseCase(DomainEventRepository domainEventRepository) {
        this.repository = domainEventRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<SaveBookCommand> commandMono) {
        return commandMono.flatMapMany(command -> repository.findById(command.getBookStoreQuoteId())
                .collectList()
                .flatMapIterable(events -> {
                    BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuoteId());
                    if (events.isEmpty()) {
                        BookStoreQuotes bookStoreQuotes = new BookStoreQuotes(
                                new Title(command.getTitle()),
                                new Author(command.getAuthor()),
                                new Stock(command.getStock()),
                                new PublicationYear(command.getPublicationYear()),
                                new Price(command.getPrice()),
                                new Type(command.getType())
                        );
                        return bookStoreQuotes.getUncommittedChanges();
                    } else {
                        BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events);
                        bookStoreQuotes.addCopy(
                                new Title(command.getTitle()),
                                new Author(command.getAuthor()),
                                new Stock(command.getStock()),
                                new PublicationYear(command.getPublicationYear()),
                                new Price(command.getPrice()),
                                new Type(command.getType())
                        );
                        return bookStoreQuotes.getUncommittedChanges();
                    }
                })
                .map(event -> event)
                .flatMap(repository::saveEvent)
        );
    }
}
