package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.models.Person;
import com.pichincha.exam.users.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.users.util.MockUtil.buildPerson;
import static com.pichincha.exam.users.util.MockUtil.buildPersonEntity;
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
        StepVerifier.create(personService.postPerson(buildPerson()))
                .expectNextMatches(person -> person.getGender().equals(Person.GenderEnum.MALE))
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
        StepVerifier.create(personService.postPerson(buildPerson()))
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