package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Flux<CustomerDto> findAll();
    Mono<CustomerDto> saveCustomer(CustomerDto customerDto);
    Mono<CustomerDto> findCustomerById(String id);
    Mono<CustomerDto> findCustomerByUserId(String userId);
    Mono<CustomerDto> updateCustomer(String id, CustomerDto customerDto);
    Mono<Void> deleteById(String id);
}
