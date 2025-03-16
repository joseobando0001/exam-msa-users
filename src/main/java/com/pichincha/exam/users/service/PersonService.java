package com.pichincha.exam.users.service;

import com.pichincha.exam.models.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<Person> postPerson(Person person);

    Flux<Person> getPersonByFilter();
}
