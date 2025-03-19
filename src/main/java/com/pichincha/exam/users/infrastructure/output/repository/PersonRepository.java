package com.pichincha.exam.users.infrastructure.output.repository;

import com.pichincha.exam.users.infrastructure.output.repository.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
    Mono<Person> findByPhone(String phone);
}

