package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.MailCampaign;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MailCampaignRepository extends ReactiveMongoRepository<MailCampaign, String> {
    Mono<MailCampaign> findMailCampaignByUserId(String userId);
}
