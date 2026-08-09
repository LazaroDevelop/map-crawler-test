package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.MailCampaignConstants;
import net.space.developer.googlemapcrawler.dto.MailCampaignDto;
import net.space.developer.googlemapcrawler.repository.MailCampaignRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MailCampaignService implements IMailCampaignService{

    private final MailCampaignRepository campaignRepository;

    @Override
    public Flux<MailCampaignDto> findAll() {
        return campaignRepository.findAll()
                .map(Utils::campaignEntityToDto);
    }

    @Override
    public Mono<MailCampaignDto> saveCampaign(MailCampaignDto campaignDto) {
        return Mono.just(campaignDto)
                .map(Utils::campaignDtoToEntity)
                .flatMap(campaignRepository::insert)
                .map(Utils::campaignEntityToDto);
    }

    @Override
    public Mono<MailCampaignDto> findCampaignById(String id) {
        return campaignRepository.findById(id)
                .map(Utils::campaignEntityToDto);
    }

    @Override
    public Mono<MailCampaignDto> findCampaignByUserId(String userId) {
        return campaignRepository.findById(userId)
                .map(Utils::campaignEntityToDto);
    }

    @Override
    public Mono<MailCampaignDto> updateCampaign(String id, MailCampaignDto campaignDto) {
        return campaignRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(MailCampaignConstants.MAIL_CAMPAIGN_NOT_FOUND, id)
                )))
                .flatMap(i ->  {
                    BeanUtils.copyProperties(campaignDto, i);
                    i.setId(id);
                    return this.campaignRepository.save(i);
                })
                .map(Utils::campaignEntityToDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return campaignRepository.deleteById(id);
    }
}
