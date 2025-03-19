package com.pichincha.exam.users.infrastructure.output.repository;

import com.pichincha.exam.users.infrastructure.output.repository.entity.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Client, Long> {

    Flux<Client> findAllByStatus(Boolean status);
}

