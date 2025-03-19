package com.pichincha.exam.users.application.port;

import com.pichincha.exam.models.ClientRequest;
import com.pichincha.exam.models.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Void> deleteCustomer(String clientId);

    Flux<ClientResponse> getCustomerByFilter();

    Mono<ClientResponse> getCustomerById(String clientId);

    Mono<ClientResponse> postCustomer(ClientRequest client);

    Mono<ClientResponse> putCustomer(String userId, ClientRequest client);
}
