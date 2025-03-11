package com.pichincha.exam.users.service;

import com.pichincha.exam.models.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Void> deleteCustomer(String clientId);

    Flux<Client> getCustomerByFilter();

    Mono<Client> getCustomerById(String clientId);

    Mono<Client> postCustomer(Client client);

    Mono<Client> putCustomer(String userId, Client client);
}
