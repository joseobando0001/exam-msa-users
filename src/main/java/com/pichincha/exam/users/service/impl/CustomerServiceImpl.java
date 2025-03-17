package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.models.Client;
import com.pichincha.exam.users.configuration.PublisherEvent;
import com.pichincha.exam.users.exception.ClientNotFound;
import com.pichincha.exam.users.helper.mapper.ClientMapper;
import com.pichincha.exam.users.repository.CustomerRepository;
import com.pichincha.exam.users.repository.PersonRepository;
import com.pichincha.exam.users.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static com.pichincha.exam.users.util.constants.ErrorConstants.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final PublisherEvent publisherEvent;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<Void> deleteCustomer(String clientId) {
        log.info("Customer id deleted {}", clientId);
        return customerRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .flatMap(client -> {
                    client.setStatus(Boolean.FALSE);
                    return customerRepository.save(client);
                })
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for delete for client {}", throwable.getMessage()))
                .then();
    }

    @Override
    public Flux<Client> getCustomerByFilter() {
        log.info("Customers obtained ");
        return customerRepository.findAllByStatus(Boolean.TRUE)
                .flatMap(this::findByIdAndMapper)
                .doOnError(throwable -> log.error("Error for get client's {}", throwable.getMessage()));
    }

    @Override
    public Mono<Client> getCustomerById(String clientId) {
        log.info("Client id {}", clientId);
        return customerRepository.findById(Long.valueOf(clientId))
                .flatMap(this::findByIdAndMapper)
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .doOnError(throwable -> log.error("Error for get client {}", throwable.getMessage()));
    }

    @Override
    public Mono<Client> postCustomer(Client client) {
        log.info("Customer created {}", client);
        return personRepository.findByPhone(client.getPhone())
                .flatMap(person ->
                {
                    com.pichincha.exam.users.domain.entity.Client entity = new com.pichincha.exam.users.domain.entity.Client();
                    entity.setPassword(hashingPassForEntity(client.getPassword()));
                    entity.setStatus(client.getStatus());
                    entity.setPersonId(person.getId());
                    return customerRepository.save(entity)
                            .doOnSuccess(publisherEvent::sendMessage)
                            .map(clientMono -> ClientMapper.INSTANCE.clientEntityToDto(person, clientMono));
                })
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for creation of client {}", throwable.getMessage()));
    }

    @Override
    public Mono<Client> putCustomer(String userId, Client client) {
        log.info("Customer updated id {} {}", userId, client);
        return customerRepository.findById(Long.valueOf(userId))
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .flatMap(this::findByIdAndMapper)
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for update customer {}", throwable.getMessage()));
    }

    @SneakyThrows
    private String hashingPassForEntity(String password) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
    }

    private Mono<Client> findByIdAndMapper(com.pichincha.exam.users.domain.entity.Client client) {
        return personRepository.findById(client.getPersonId())
                .map(person -> ClientMapper.INSTANCE.clientEntityToDto(person, client));
    }
}
