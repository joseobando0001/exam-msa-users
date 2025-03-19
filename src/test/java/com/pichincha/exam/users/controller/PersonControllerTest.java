package com.pichincha.exam.users.controller;

import com.pichincha.exam.users.application.port.PersonService;
import com.pichincha.exam.users.infrastructure.input.adapter.rest.impl.PersonController;
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

import static com.pichincha.exam.users.util.MockUtil.buildPersonRequest;
import static com.pichincha.exam.users.util.MockUtil.buildPersonResponse;
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
        when(personService.postPerson(any())).thenReturn(Mono.just(buildPersonResponse()));
        StepVerifier.create(personController.postPerson((
                                Mono.just(buildPersonRequest())),
                        MockServerWebExchange.from(MockServerHttpRequest.post("http://localhost/person").build())))
                .expectNextMatches(clientResponseEntity -> clientResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify()
        ;
    }

    @Test
    void getPersonByFilter() {
        when(personService.getPersonByFilter()).thenReturn(Flux.just(buildPersonResponse()));
        StepVerifier.create(personController.getPersonByFilter(
                        MockServerWebExchange.from(MockServerHttpRequest.get("http://localhost/person").build())))
                .expectNextMatches(clientResponseEntity -> clientResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify();
    }
}