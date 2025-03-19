package com.pichincha.exam.users.application.service.impl;

import com.pichincha.exam.models.PersonRequest;
import com.pichincha.exam.models.PersonResponse;
import com.pichincha.exam.users.application.port.PersonService;
import com.pichincha.exam.users.infrastructure.output.adapter.mapper.PersonMapper;
import com.pichincha.exam.users.infrastructure.output.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<PersonResponse> postPerson(PersonRequest person) {
        return personRepository.save(PersonMapper.INSTANCE.clientDtoToEntity(person))
                .map(PersonMapper.INSTANCE::clientEntityToDto)
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for create a person {}", throwable.getMessage()));
    }

    @Override
    public Flux<PersonResponse> getPersonByFilter() {
        log.info("People obtained ");
        return personRepository.findAll()
                .map(PersonMapper.INSTANCE::clientEntityToDto)
                .doOnError(throwable -> log.error("Error for get people's {}", throwable.getMessage()));
    }
}
