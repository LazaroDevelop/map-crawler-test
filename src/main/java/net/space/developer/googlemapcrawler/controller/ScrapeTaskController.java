package net.space.developer.googlemapcrawler.controller;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.ScrapeTaskDto;
import net.space.developer.googlemapcrawler.services.IScrapeTaskService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class ScrapeTaskController {

    private final IScrapeTaskService taskService;

    @GetMapping("/scrape/task/all")
    public Flux<ScrapeTaskDto> getAll(){
        return taskService.findAll();
    }

    @GetMapping("/scrape/task/{id}")
    public Mono<ScrapeTaskDto> findById(@PathVariable("id") String id){
        return taskService.findScrapeById(id);
    }

    @GetMapping("/scrape/task/user/{userId}")
    public Mono<ScrapeTaskDto> findByUserId(@PathVariable("userId") String userId){
        return taskService.findScrapeByUserId(userId);
    }

    @PostMapping("/scrape/task/save")
    public Mono<ScrapeTaskDto> save(@RequestBody ScrapeTaskDto scrapeTaskDto){
        return taskService.saveScrapeTask(scrapeTaskDto);
    }

    @PutMapping("/scrape/task/update/{id}")
    public Mono<ScrapeTaskDto> update(@PathVariable("id") String id, @RequestBody ScrapeTaskDto scrapeTaskDto){
        return taskService.updateScrapeTask(id, scrapeTaskDto);
    }

    @DeleteMapping("/scrape/task/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return taskService.deleteScrapeTaskById(id);
    }
}
