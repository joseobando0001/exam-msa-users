package com.pichincha.exam.users.controller;

import com.pichincha.exam.users.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.users.util.MockUtil.buildClient;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockServerHttpRequest request;

    private static final String LOCAL = "http://localhost/customers";

    @BeforeEach
    void setUp() {
    }

    @Test
    void deleteCustomer() {
        doReturn(Mono.empty()).when(customerService).deleteCustomer(any());
        request = MockServerHttpRequest.delete(LOCAL).build();
        StepVerifier.create(customerController.deleteCustomer("1", MockServerWebExchange.from(request)))
                .expectNextMatches(voidResponseEntity -> voidResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(204)))
                .expectComplete();
    }

    @Test
    void getCustomerByFilter() {
        when(customerService.getCustomerByFilter()).thenReturn(Flux.just(buildClient()));
        request = MockServerHttpRequest.get(LOCAL).build();
        StepVerifier.create(customerController.getCustomerByFilter(MockServerWebExchange.from(request)))
                .expectComplete();
    }

    @Test
    void getCustomerById() {
        when(customerService.getCustomerById(any())).thenReturn(Mono.just(buildClient()));
        request = MockServerHttpRequest.get(LOCAL).build();
        StepVerifier.create(customerController.getCustomerById("1", MockServerWebExchange.from(request)))
                .expectComplete();
    }

    @Test
    void postCustomer() {
        lenient().when(customerService.postCustomer(any())).thenReturn(Mono.just(buildClient()));
        request = MockServerHttpRequest.post(LOCAL).build();
        StepVerifier.create(customerController.postCustomer(Mono.just(buildClient()), MockServerWebExchange.from(request)))
                .expectComplete();
    }

    @Test
    void putCustomer() {
        lenient().when(customerService.putCustomer(any(), any())).thenReturn(Mono.just(buildClient()));
        request = MockServerHttpRequest.put(LOCAL).build();
        StepVerifier.create(customerController.putCustomer("1", Mono.just(buildClient()), MockServerWebExchange.from(request)))
                .expectComplete();
    }

}