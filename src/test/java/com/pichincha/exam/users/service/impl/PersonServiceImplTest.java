package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.models.PersonResponse;
import com.pichincha.exam.users.application.service.impl.PersonServiceImpl;
import com.pichincha.exam.users.infrastructure.output.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.users.util.MockUtil.buildPersonEntity;
import static com.pichincha.exam.users.util.MockUtil.buildPersonRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    PersonRepository personRepository;

    @Mock
    TransactionalOperator transactionalOperator;

    @Test
    void postPerson() {
        when(personRepository.save(any())).thenReturn(Mono.just(buildPersonEntity()));
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        StepVerifier.create(personService.postPerson(buildPersonRequest()))
                .expectNextMatches(person -> person.getGender().equals(PersonResponse.GenderEnum.MALE))
                .expectComplete()
                .verify();
    }


    @Test
    void deletePersonWithRollback() {
        when(personRepository.save(any())).thenReturn(Mono.error(new RuntimeException()));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> {
                    Mono<?> mono = invocation.getArgument(0);
                    return mono.onErrorResume(Mono::error);
                });
        StepVerifier.create(personService.postPerson(buildPersonRequest()))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException)
                .verify();
    }

    @Test
    void getPersonByFilter() {
        when(personRepository.findAll()).thenReturn(Flux.just(buildPersonEntity()));
        StepVerifier.create(personService.getPersonByFilter())
                .expectComplete();
    }
}