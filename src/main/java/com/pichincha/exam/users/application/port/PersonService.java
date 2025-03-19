package com.pichincha.exam.users.application.port;

import com.pichincha.exam.models.PersonRequest;
import com.pichincha.exam.models.PersonResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonResponse> postPerson(PersonRequest person);

    Flux<PersonResponse> getPersonByFilter();
}
