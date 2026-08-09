package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.Smtp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SmtpRepository extends ReactiveMongoRepository<Smtp, String> {
    Flux<Smtp> findByUserId(String userId);
}
