package com.pichincha.exam.users.controller;

import com.pichincha.exam.users.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.users.util.MockUtil.buildPerson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @InjectMocks
    PersonController personController;

    @Mock
    PersonService personService;

    @Test
    void postPerson() {
        when(personService.postPerson(any())).thenReturn(Mono.just(buildPerson()));
        StepVerifier.create(personController.postPerson((
                                Mono.just(buildPerson())),
                        MockServerWebExchange.from(MockServerHttpRequest.post("http://localhost/person").build())))
                .expectNextMatches(clientResponseEntity -> clientResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify()
        ;
    }

    @Test
    void getPersonByFilter() {
        when(personService.getPersonByFilter()).thenReturn(Flux.just(buildPerson()));
        StepVerifier.create(personController.getPersonByFilter(
                        MockServerWebExchange.from(MockServerHttpRequest.get("http://localhost/person").build())))
                .expectNextMatches(clientResponseEntity -> clientResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify();
    }
}