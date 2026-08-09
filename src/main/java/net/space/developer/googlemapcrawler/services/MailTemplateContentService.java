package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.MailCampaignConstants;
import net.space.developer.googlemapcrawler.dto.MailTemplateDto;
import net.space.developer.googlemapcrawler.repository.MailTemplateContentRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MailTemplateContentService implements IMailTemplateContentService{

    private final MailTemplateContentRepository contentRepository;

    @Override
    public Flux<MailTemplateDto> findAll() {
        return contentRepository.findAll()
                .map(Utils::mailTemplateEntityToDto);
    }

    @Override
    public Mono<MailTemplateDto> saveMailTemplate(MailTemplateDto mailTemplateDto) {
        return Mono.just(mailTemplateDto)
                .map(Utils::mailTemplateDtoToEntity)
                .flatMap(contentRepository::insert)
                .map(Utils::mailTemplateEntityToDto);
    }

    @Override
    public Mono<MailTemplateDto> findMailTemplateById(String id) {
        return contentRepository.findById(id)
                .map(Utils::mailTemplateEntityToDto);
    }

    @Override
    public Mono<MailTemplateDto> findMailTemplateByUserId(String userId) {
        return contentRepository.findMailTemplateContentByUserId(userId)
                .map(Utils::mailTemplateEntityToDto);
    }

    @Override
    public Mono<MailTemplateDto> updateMailTemplate(String id, MailTemplateDto templateDto) {
        return contentRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(MailCampaignConstants.MAIL_TEMPLATE_NOT_FOUND, id)
                )))
                .flatMap(i -> {
                    BeanUtils.copyProperties(templateDto, i);
                    i.setId(id);
                    return contentRepository.save(i);
                })
                .map(Utils::mailTemplateEntityToDto);
    }

    @Override
    public Mono<Void> deleteMailTemplateById(String id) {
        return contentRepository.deleteById(id);
    }
}
