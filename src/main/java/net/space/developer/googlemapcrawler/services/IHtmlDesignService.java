package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.HtmlDesignDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHtmlDesignService {
    Mono<HtmlDesignDto> save(HtmlDesignDto htmlDesignDto);
    Mono<HtmlDesignDto> update(String id, HtmlDesignDto htmlDesignDto);
    Mono<HtmlDesignDto> findById(String id);
    Flux<HtmlDesignDto> findAll();
    Mono<Void> delete(String id);
}
