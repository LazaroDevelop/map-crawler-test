package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.Contacts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ContactsRepository extends ReactiveMongoRepository<Contacts, String> {
    Mono<Contacts> findContactsByUserId(String userId);
}
