package com.pichincha.exam.users.repository;

import com.pichincha.exam.users.domain.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
    Mono<Person> findByPhone(String phone);
}

