package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.Customers;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customers, String> {
    Mono<Customers> findCustomersByUserId(String userId);
}
