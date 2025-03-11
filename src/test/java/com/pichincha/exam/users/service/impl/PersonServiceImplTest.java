package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.models.Person;
import com.pichincha.exam.users.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    void postPerson() {
        when(personRepository.save(any())).thenReturn(Mono.just(buildPersonEntity()));
        StepVerifier.create(personService.postPerson(buildPerson()))
                .expectNextMatches(person -> person.getGender().equals(Person.GenderEnum.MALE))
                .expectComplete()
                .verify();
    }
}