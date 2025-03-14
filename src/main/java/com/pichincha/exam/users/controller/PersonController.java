package com.pichincha.exam.users.controller;

import com.pichincha.exam.api.PersonApi;
import com.pichincha.exam.models.Person;
import com.pichincha.exam.users.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonController implements PersonApi {
    private final PersonService personService;

    @Override
    public Mono<ResponseEntity<Person>> postPerson(Mono<Person> person, ServerWebExchange exchange) {
        log.info("Person created");
        return person.flatMap(
                body -> personService.postPerson(body)
                        .map(personB -> ResponseEntity.ok().body(personB))
        );
    }
}
