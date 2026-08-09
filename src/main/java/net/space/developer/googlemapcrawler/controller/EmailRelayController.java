package net.space.developer.googlemapcrawler.controller;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.EmailMessageDto;
import net.space.developer.googlemapcrawler.services.IEmailRelayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class EmailRelayController {

    private final IEmailRelayService relayService;

    @PostMapping("/send-email/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Void>> sendEmail(
            @PathVariable("userId") String id,
            @RequestBody EmailMessageDto messageDto
    ){
        return this.relayService.sendEmail(id, messageDto)
                .map(ResponseEntity::ok);
    }
}
