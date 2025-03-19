package com.pichincha.exam.users.service.impl;

import com.pichincha.exam.users.application.service.impl.CustomerServiceImpl;
import com.pichincha.exam.users.infrastructure.configuration.PublisherEvent;
import com.pichincha.exam.users.infrastructure.output.repository.CustomerRepository;
import com.pichincha.exam.users.infrastructure.output.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.users.util.MockUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    PublisherEvent publisherEvent;

    @Mock
    TransactionalOperator transactionalOperator;


    @BeforeEach
    void setUp() {
    }

    @Test
    void deleteCustomer() {
        when(customerRepository.findById(any(Long.class))).thenReturn(Mono.just(buildClientEntity()));
        when(customerRepository.save(any())).thenReturn(Mono.just(buildClientEntity()));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        StepVerifier.create(customerService.deleteCustomer("1"))
                .expectComplete()
                .verify();

    }

    @Test
    void getCustomerByFilter() {
        when(customerRepository.findAllByStatus(any())).thenReturn(Flux.just(buildClientEntity()));
        StepVerifier.create(customerService.getCustomerByFilter())
                .expectComplete();
    }

    @Test
    void getCustomerById() {
        when(customerRepository.findById(any(Long.class))).thenReturn(Mono.just(buildClientEntity()));
        when(personRepository.findById(any(Long.class))).thenReturn(Mono.just(buildPersonEntity()));
        StepVerifier.create(customerService.getCustomerById("1"))
                .expectNextMatches(client -> !client.getPassword().isBlank())
                .expectComplete()
                .verify();

    }

    @Test
    void postCustomer() {
        when(personRepository.findByPhone(any())).thenReturn(Mono.just(buildPersonEntity()));
        when(customerRepository.save(any())).thenReturn(Mono.just(buildClientEntity()));
        when(publisherEvent.sendMessage(any())).thenReturn(Mono.just(Boolean.TRUE));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        StepVerifier.create(customerService.postCustomer(buildClientRequest()))
                .expectNextMatches(client -> !client.getPhone().isBlank())
                .expectComplete()
                .verify();
    }

    @Test
    void putCustomer() {
        when(customerRepository.findById(any(Long.class))).thenReturn(Mono.just(buildClientEntity()));
        when(personRepository.findById(any(Long.class))).thenReturn(Mono.just(buildPersonEntity()));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        StepVerifier.create(customerService.putCustomer("1", buildClientRequest()))
                .expectNextMatches(client -> !client.getAddress().isBlank())
                .expectComplete()
                .verify();
    }
}