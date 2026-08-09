package net.space.developer.googlemapcrawler.repository;

import net.space.developer.googlemapcrawler.entity.CSVInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSVInfoRepository extends ReactiveMongoRepository<CSVInfo, String> {
}
