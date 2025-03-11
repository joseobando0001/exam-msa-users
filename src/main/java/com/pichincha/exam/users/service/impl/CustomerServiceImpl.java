package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.models.Client;
import com.pichincha.exam.users.exception.ClientNotFound;
import com.pichincha.exam.users.helper.mapper.ClientMapper;
import com.pichincha.exam.users.repository.CustomerRepository;
import com.pichincha.exam.users.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.pichincha.exam.users.util.constants.ErrorConstants.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Mono<Void> deleteCustomer(String clientId) {
        log.info("Customer id deleted {}", clientId);
        return customerRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .doOnSuccess(client -> customerRepository.deleteById(client.getId()))
                .doOnError(throwable -> log.error("Error for delete for client {}", throwable.getMessage()))
                .then();

    }

    @Override
    public Flux<Client> getCustomerByFilter() {
        log.info("Customers obtained ");
        return customerRepository.findAll()
                .map(ClientMapper.INSTANCE::clientEntityToDto)
                .doOnError(throwable -> log.error("Error for get client's {}", throwable.getMessage()));
    }

    @Override
    public Mono<Client> getCustomerById(String clientId) {
        log.info("Client id {}", clientId);
        return customerRepository.findById(Long.valueOf(clientId))
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .map(ClientMapper.INSTANCE::clientEntityToDto)
                .doOnError(throwable -> log.error("Error for get client {}", throwable.getMessage()));
    }

    @Override
    public Mono<Client> postCustomer(Client client) {
        log.info("Customer created {}", client);
        return customerRepository.save(ClientMapper.INSTANCE.clientDtoToEntity(client))
                .map(ClientMapper.INSTANCE::clientEntityToDto)
                .doOnError(throwable -> log.error("Error for creation of client {}", throwable.getMessage()));
    }

    @Override
    public Mono<Client> putCustomer(String userId, Client client) {
        log.info("Customer updated id {} {}", userId, client);
        return customerRepository.findById(Long.valueOf(userId))
                .switchIfEmpty(Mono.error(new ClientNotFound(NOT_FOUND)))
                .map(client1 -> ClientMapper.INSTANCE.clientDtoToEntity(client))
                .map(ClientMapper.INSTANCE::clientEntityToDto)
                .doOnError(throwable -> log.error("Error for update customer {}", throwable.getMessage()));
    }
}
