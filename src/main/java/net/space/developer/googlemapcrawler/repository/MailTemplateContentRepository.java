package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.MailTemplateContent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MailTemplateContentRepository extends ReactiveMongoRepository<MailTemplateContent, String> {
    Mono<MailTemplateContent> findMailTemplateContentByUserId(String userId);
}
