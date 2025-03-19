package com.pichincha.exam.users.infrastructure.input.adapter.rest.impl;

import com.pichincha.exam.api.PersonApi;
import com.pichincha.exam.models.PersonRequest;
import com.pichincha.exam.models.PersonResponse;
import com.pichincha.exam.users.application.port.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonController implements PersonApi {
    private final PersonService personService;


    @Override
    public Mono<ResponseEntity<PersonResponse>> postPerson(Mono<PersonRequest> personRequest, ServerWebExchange exchange) {
        log.info("Person created");
        return personRequest.flatMap(
                body -> personService.postPerson(body)
                        .map(personB -> ResponseEntity.ok().body(personB))
        );
    }

    @Override
    public Mono<ResponseEntity<Flux<PersonResponse>>> getPersonByFilter(ServerWebExchange exchange) {
        log.info("Get people");
        return personService.getPersonByFilter()
                .collectList()
                .map(client -> ResponseEntity.ok().body(Flux.fromIterable(client)));
    }
}
