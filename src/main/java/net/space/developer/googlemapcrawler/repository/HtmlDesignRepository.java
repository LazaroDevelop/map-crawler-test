package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.HtmlDesign;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlDesignRepository extends ReactiveMongoRepository<HtmlDesign, String> {
}
