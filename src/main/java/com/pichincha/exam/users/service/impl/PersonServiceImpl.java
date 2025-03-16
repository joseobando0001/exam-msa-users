package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.models.Person;
import com.pichincha.exam.users.helper.mapper.PersonMapper;
import com.pichincha.exam.users.repository.PersonRepository;
import com.pichincha.exam.users.service.PersonService;
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
    public Mono<Person> postPerson(Person person) {
        return personRepository.save(PersonMapper.INSTANCE.clientDtoToEntity(person))
                .map(PersonMapper.INSTANCE::clientEntityToDto)
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for create a person {}", throwable.getMessage()));
    }

    @Override
    public Flux<Person> getPersonByFilter() {
        log.info("People obtained ");
        return personRepository.findAll()
                .map(PersonMapper.INSTANCE::clientEntityToDto)
                .doOnError(throwable -> log.error("Error for get people's {}", throwable.getMessage()));
    }
}
