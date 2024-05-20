package com.penguin;

import com.penguin.usecases.calculatepricewithbudgetusecase.CalculatePriceWithBudgetUseCase;
import com.penguin.usecases.calculateproductspricegroupsuseCase.CalculateProductsPriceGroupsUseCase;
import com.penguin.usecases.calculateproductspriceusecase.CalculateProductsPriceUseCase;
import com.penguin.gateways.DomainEventRepository;
import com.penguin.usecases.getallcopiesusecase.GetAllCopiesUseCase;
import com.penguin.model.bookStoreQuotes.commands.CalculateMultiplePriceCommand;
import com.penguin.model.bookStoreQuotes.commands.CalculateMultiplePriceGroupsCommand;
import com.penguin.model.bookStoreQuotes.commands.CalculatePriceWithBudgetCommand;
import com.penguin.model.bookStoreQuotes.commands.SaveBookCommand;
import com.penguin.model.bookStoreQuotes.values.responses.*;
import com.penguin.usecases.saveCopyUseCase.SaveCopyUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
public class RouterRest {

    private final SaveCopyUseCase saveCopyUseCase;
    private final CalculateProductsPriceUseCase calculateProductsPriceUseCase;
    private final CalculatePriceWithBudgetUseCase calculatePriceWithBudgetUseCase;

    private  final CalculateProductsPriceGroupsUseCase calculateProductsPriceGroupsUseCase;
    private final GetAllCopiesUseCase getAllCopiesUseCase;
    private final DomainEventRepository domainEventRepository;

    public RouterRest(SaveCopyUseCase saveCopyUseCase, CalculateProductsPriceUseCase calculateProductsPriceUseCase,
                      CalculatePriceWithBudgetUseCase calculatePriceWithBudgetUseCase
                       ,CalculateProductsPriceGroupsUseCase calculateProductsPriceGroupsUseCase,
                      GetAllCopiesUseCase getAllCopiesUseCase
            , DomainEventRepository domainEventRepository) {
        this.saveCopyUseCase = saveCopyUseCase;
        this.calculateProductsPriceUseCase = calculateProductsPriceUseCase;
        this.calculatePriceWithBudgetUseCase = calculatePriceWithBudgetUseCase;
        this.calculateProductsPriceGroupsUseCase = calculateProductsPriceGroupsUseCase;
        this.getAllCopiesUseCase = getAllCopiesUseCase;
        this.domainEventRepository = domainEventRepository;

    }

    @PostMapping("/save-copy")
    public Mono<ResponseEntity<SimplifiedBookResponse>> saveCopy(@RequestBody SaveBookCommand command) {
        return saveCopyUseCase.mapToSimplifiedResponse(Mono.just(command))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/calculate-price")
    public Mono<ResponseEntity<SimplifiedCalculatedMultiplePrice>> calculateMultiplePrice(@RequestBody CalculateMultiplePriceCommand command) {
        return calculateProductsPriceUseCase.calculateAndSimplify(Mono.just(command))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/calculate-price-with-budget")
    public Mono<ResponseEntity<SimplifiedCalculatedPriceWithBudget>> calculatePriceWithBudget(@RequestBody CalculatePriceWithBudgetCommand command) {
        return calculatePriceWithBudgetUseCase.calculateAndSimplify(Mono.just(command))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/calculate-price-groups")
    public Mono<ResponseEntity<SimplifiedCalculatedMultiplePriceGroups>> calculateMultiplePriceGroups(@RequestBody CalculateMultiplePriceGroupsCommand command) {
        return calculateProductsPriceGroupsUseCase.calculateAndSimplify(Mono.just(command))
                .map(ResponseEntity::ok);
    }



    @GetMapping("/book-store-quotes-id")
    public Mono<ResponseEntity<String>> getBookStoreQuotesId() {
        return domainEventRepository.findAll()
                .map(event -> event.aggregateRootId())
                .distinct()
                .next()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }



    @GetMapping("/all-copies")
    public Mono<ResponseEntity<Flux<AllCopiesResponse>>> getAllCopies() {
        return Mono.just(ResponseEntity.ok(getAllCopiesUseCase.getAllCopies()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        String errorMessage = "Error interno del servidor: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }



}
