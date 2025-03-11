package com.pichincha.exam.users.controller;

import com.pichincha.exam.api.CustomersApi;
import com.pichincha.exam.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController implements CustomersApi {
    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String userId, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Flux<Client>>> getCustomerByFilter(ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Client>> getCustomerById(String userId, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Client>> postCustomer(Mono<Client> client, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Client>> putCustomer(String userId, Mono<Client> client, ServerWebExchange exchange) {
        return null;
    }
}
