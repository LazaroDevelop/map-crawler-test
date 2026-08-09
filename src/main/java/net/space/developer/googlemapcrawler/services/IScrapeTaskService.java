package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.ScrapeTaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IScrapeTaskService {
    Flux<ScrapeTaskDto> findAll();
    Mono<ScrapeTaskDto> saveScrapeTask(ScrapeTaskDto scrapeTaskDto);
    Mono<ScrapeTaskDto> findScrapeById(String id);
    Mono<ScrapeTaskDto> findScrapeByUserId(String userId);
    Mono<ScrapeTaskDto> updateScrapeTask(String id, ScrapeTaskDto scrapeTaskDto);
    Mono<Void> deleteScrapeTaskById(String id);
}
