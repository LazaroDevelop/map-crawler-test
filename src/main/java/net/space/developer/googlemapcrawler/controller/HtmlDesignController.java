package net.space.developer.googlemapcrawler.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.HtmlDesignDto;
import net.space.developer.googlemapcrawler.services.IHtmlDesignService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class HtmlDesignController {

    private final IHtmlDesignService designService;

    @PostMapping("/html-design/add")
    public Mono<HtmlDesignDto> addHtmlDesign(@RequestBody HtmlDesignDto htmlDesignDto) {
        return designService.save(htmlDesignDto);
    }

    @GetMapping("/html-design/{id}")
    public Mono<HtmlDesignDto> getHtmlDesign(@PathVariable String id) {
        return designService.findById(id);
    }

    @GetMapping("/html-design/all")
    public Flux<HtmlDesignDto> getAllHtmlDesign() {
        return designService.findAll();
    }

    @PutMapping("/html-design/update/{id}")
    public Mono<HtmlDesignDto> updateHtmlDesign(@PathVariable("id") String id,@RequestBody HtmlDesignDto htmlDesignDto) {
        return designService.update(id, htmlDesignDto);
    }

    @DeleteMapping("/html-design/delete/{id}")
    public Mono<Void> deleteHtmlDesign(@PathVariable String id) {
        return designService.delete(id);
    }

}
