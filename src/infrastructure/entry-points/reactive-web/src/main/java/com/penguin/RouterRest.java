package com.penguin;

import com.penguin.model.bookStoreQuotes.commands.SaveBookCommand;
import com.penguin.model.generic.DomainEvent;
import com.penguin.saveCopyUseCase.SaveCopyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RouterRest {

    private final SaveCopyUseCase saveCopyUseCase;

    public RouterRest(SaveCopyUseCase saveCopyUseCase) {
        this.saveCopyUseCase = saveCopyUseCase;
    }

    @PostMapping("/save-copy")
    public Mono<ResponseEntity<List<DomainEvent>>> saveCopy(@RequestBody SaveBookCommand command){
        return saveCopyUseCase.apply(Mono.just(command))
                .collectList()
                .map(ResponseEntity::ok);

    }

    @PostMapping("/calculate-price")
    public Mono<ResponseEntity<List<DomainEvent>>> calculateMultiplePrice(@RequestBody){

    }

}
