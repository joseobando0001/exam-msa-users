package com.pichincha.exam.users.controller;

import com.pichincha.exam.api.CustomersApi;
import com.pichincha.exam.models.Client;
import com.pichincha.exam.users.service.CustomerService;
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
    public Mono<ResponseEntity<Flux<Client>>> getCustomerByFilter(ServerWebExchange exchange) {
        log.info("Get customers");
        return customerService.getCustomerByFilter()
                .collectList()
                .map(client -> ResponseEntity.ok().body(Flux.fromIterable(client)));
    }

    @Override
    public Mono<ResponseEntity<Client>> getCustomerById(String userId, ServerWebExchange exchange) {
        log.info("Get customer by id {}", userId);
        return customerService.getCustomerById(userId)
                .map(client -> ResponseEntity.ok().body(client));
    }

    @Override
    public Mono<ResponseEntity<Client>> postCustomer(Mono<Client> client, ServerWebExchange exchange) {
        log.info("Customer created");
        return client.flatMap(body ->
                customerService.postCustomer(body)
                        .map(clientB ->
                                ResponseEntity.ok().body(clientB))
        );
    }

    @Override
    public Mono<ResponseEntity<Client>> putCustomer(String userId, Mono<Client> client, ServerWebExchange exchange) {
        return client.flatMap(body ->
                customerService.putCustomer(userId, body)
                        .map(clientB ->
                                ResponseEntity.ok().body(clientB)));
    }
}
