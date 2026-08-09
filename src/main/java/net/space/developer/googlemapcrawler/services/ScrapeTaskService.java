package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.ScrapConstants;
import net.space.developer.googlemapcrawler.dto.ScrapeTaskDto;
import net.space.developer.googlemapcrawler.repository.ScrapeTaskRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ScrapeTaskService implements IScrapeTaskService{

    private final ScrapeTaskRepository taskRepository;

    @Override
    public Flux<ScrapeTaskDto> findAll() {
        return taskRepository.findAll()
                .map(Utils::scrapeTaskEntityToDto);
    }

    @Override
    public Mono<ScrapeTaskDto> saveScrapeTask(ScrapeTaskDto scrapeTaskDto) {
        return Mono.just(scrapeTaskDto)
                .map(Utils::scrapeTaskDtoToEntity)
                .flatMap(taskRepository::insert)
                .map(Utils::scrapeTaskEntityToDto);
    }

    @Override
    public Mono<ScrapeTaskDto> findScrapeById(String id) {
        return this.taskRepository.findById(id)
                .map(Utils::scrapeTaskEntityToDto);
    }

    @Override
    public Mono<ScrapeTaskDto> findScrapeByUserId(String userId) {
        return this.taskRepository.findById(userId)
                .map(Utils::scrapeTaskEntityToDto);
    }

    @Override
    public Mono<ScrapeTaskDto> updateScrapeTask(String id, ScrapeTaskDto scrapeTaskDto) {
        return this.taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(ScrapConstants.SCRAP_TASK_NOT_FOUND_EXCEPTION, id)
                )))
                .flatMap(i -> {
                    BeanUtils.copyProperties(scrapeTaskDto, i);
                    i.setId(id);
                    return this.taskRepository.save(i);
                }).map(Utils::scrapeTaskEntityToDto);
    }

    @Override
    public Mono<Void> deleteScrapeTaskById(String id) {
        return this.taskRepository.deleteById(id);
    }
}
