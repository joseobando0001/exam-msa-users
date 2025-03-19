package com.pichincha.exam.users.infrastructure.input.adapter.rest.impl;

import com.pichincha.exam.api.CustomersApi;
import com.pichincha.exam.models.ClientRequest;
import com.pichincha.exam.models.ClientResponse;
import com.pichincha.exam.users.application.port.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {
    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String userId, ServerWebExchange exchange) {
        log.info("Delete customer by id {}", userId);
        return customerService.deleteCustomer(userId)
                .then(Mono.fromSupplier(() -> ResponseEntity.noContent().build()));
    }

    @Override
    public Mono<ResponseEntity<ClientResponse>> postCustomer(Mono<ClientRequest> clientRequest, ServerWebExchange exchange) {
        log.info("Customer created");
        return clientRequest.flatMap(body ->
                customerService.postCustomer(body)
                        .map(clientB ->
                                ResponseEntity.ok().body(clientB))
        );
    }

    @Override
    public Mono<ResponseEntity<ClientResponse>> putCustomer(String userId, Mono<ClientRequest> clientRequest, ServerWebExchange exchange) {
        return clientRequest.flatMap(body ->
                customerService.putCustomer(userId, body)
                        .map(clientB ->
                                ResponseEntity.ok().body(clientB)));
    }

    @Override
    public Mono<ResponseEntity<Flux<ClientResponse>>> getCustomerByFilter(ServerWebExchange exchange) {
        log.info("Get customers");
        return customerService.getCustomerByFilter()
                .collectList()
                .map(client -> ResponseEntity.ok().body(Flux.fromIterable(client)));
    }

    @Override
    public Mono<ResponseEntity<ClientResponse>> getCustomerById(String userId, ServerWebExchange exchange) {
        log.info("Get customer by id {}", userId);
        return customerService.getCustomerById(userId)
                .map(client -> ResponseEntity.ok().body(client));
    }
}
