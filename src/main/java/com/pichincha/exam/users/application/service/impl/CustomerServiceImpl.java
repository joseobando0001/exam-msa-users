package com.pichincha.exam.users.application.service.impl;

import com.pichincha.exam.models.ClientRequest;
import com.pichincha.exam.models.ClientResponse;
import com.pichincha.exam.users.application.port.CustomerService;
import com.pichincha.exam.users.infrastructure.configuration.PublisherEvent;
import com.pichincha.exam.users.infrastructure.exception.ClientNotFound;
import com.pichincha.exam.users.infrastructure.output.adapter.mapper.ClientMapper;
import com.pichincha.exam.users.infrastructure.output.repository.CustomerRepository;
import com.pichincha.exam.users.infrastructure.output.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.pichincha.exam.users.infrastructure.constants.ErrorConstants.NOT_FOUND;

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
    public Flux<ClientResponse> getCustomerByFilter() {
        log.info("Customers obtained ");
        return customerRepository.findAllByStatus(Boolean.TRUE)
                .flatMap(this::findByIdAndMapper)
                .doOnError(throwable -> log.error("Error for get client's {}", throwable.getMessage()));
    }

    @Override
    public Mono<ClientResponse> getCustomerById(String clientId) {
        log.info("Client id {}", clientId);
        return customerRepository.findById(Long.valueOf(clientId))
                .flatMap(this::findByIdAndMapper)
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .doOnError(throwable -> log.error("Error for get client {}", throwable.getMessage()));
    }

    @Override
    public Mono<ClientResponse> postCustomer(ClientRequest client) {
        log.info("Customer created {}", client);
        return personRepository.findByPhone(client.getPhone())
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .flatMap(person ->
                        customerRepository.save(ClientMapper.INSTANCE.clientDtoToEntity(person, client))
                                .doOnSuccess(publisherEvent::sendMessage)
                                .map(clientMono -> ClientMapper.INSTANCE.clientEntityToDto(person, clientMono)))
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for creation of client {}", throwable.getMessage()));
    }

    @Override
    public Mono<ClientResponse> putCustomer(String userId, ClientRequest client) {
        log.info("Customer updated id {} {}", userId, client);
        return customerRepository.findById(Long.valueOf(userId))
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .flatMap(this::findByIdAndMapper)
                .as(transactionalOperator::transactional)
                .doOnError(throwable -> log.error("Error for update customer {}", throwable.getMessage()));
    }


    private Mono<ClientResponse> findByIdAndMapper(com.pichincha.exam.users.infrastructure.output.repository.entity.Client client) {
        return personRepository.findById(client.getPersonId())
                .map(person -> ClientMapper.INSTANCE.clientEntityToDto(person, client));
    }
}
