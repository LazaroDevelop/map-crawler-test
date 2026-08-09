package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.MailCampaignDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMailCampaignService {
    Flux<MailCampaignDto> findAll();
    Mono<MailCampaignDto> saveCampaign(MailCampaignDto campaignDto);
    Mono<MailCampaignDto> findCampaignById(String id);
    Mono<MailCampaignDto> findCampaignByUserId(String userId);
    Mono<MailCampaignDto> updateCampaign(String id, MailCampaignDto campaignDto);
    Mono<Void> deleteById(String id);
}
