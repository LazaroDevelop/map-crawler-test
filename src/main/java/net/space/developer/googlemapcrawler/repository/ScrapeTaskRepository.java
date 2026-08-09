package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.ScrapeTask;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ScrapeTaskRepository extends ReactiveMongoRepository<ScrapeTask, String> {
    Mono<ScrapeTask> findScrapeTaskByUserId(String userId);
}
