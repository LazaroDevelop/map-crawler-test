package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.MailTemplateDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMailTemplateContentService {
    Flux<MailTemplateDto> findAll();
    Mono<MailTemplateDto> saveMailTemplate(MailTemplateDto mailTemplateDto);
    Mono<MailTemplateDto> findMailTemplateById(String id);
    Mono<MailTemplateDto> findMailTemplateByUserId(String userId);
    Mono<MailTemplateDto> updateMailTemplate(String id, MailTemplateDto mailTemplateDto);
    Mono<Void> deleteMailTemplateById(String id);
}
