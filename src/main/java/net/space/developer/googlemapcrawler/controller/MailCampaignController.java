package net.space.developer.googlemapcrawler.controller;


import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.MailCampaignDto;
import net.space.developer.googlemapcrawler.dto.MailTemplateDto;
import net.space.developer.googlemapcrawler.services.IMailCampaignService;
import net.space.developer.googlemapcrawler.services.IMailTemplateContentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class MailCampaignController {

    private final IMailCampaignService campaignService;
    private final IMailTemplateContentService contentService;

    // Campaign endpoints

    @GetMapping("/mail/campaign/all")
    public Flux<MailCampaignDto> findAllCampaign(){
        return campaignService.findAll();
    }

    @GetMapping("/mail/campaign/{id}")
    public Mono<MailCampaignDto> findCampaignById(@PathVariable("id") String id){
        return campaignService.findCampaignById(id);
    }

    @GetMapping("/mail/campaign/user/{id}")
    public Mono<MailCampaignDto> findByUserId(@PathVariable("id") String id){
        return campaignService.findCampaignByUserId(id);
    }

    @PostMapping("/mail/campaign/save")
    public Mono<MailCampaignDto> saveCampaign(@RequestBody MailCampaignDto campaignDto){
        return campaignService.saveCampaign(campaignDto);
    }

    @PutMapping("/mail/campaign/update/{id}")
    public Mono<MailCampaignDto> updateCampaign(@PathVariable("id") String id, @RequestBody MailCampaignDto campaignDto){
        return campaignService.updateCampaign(id, campaignDto);
    }

    @DeleteMapping("/mail/campaign/delete/{id}")
    public Mono<Void> deleteCampaign(@PathVariable("id") String id){
        return campaignService.deleteById(id);
    }

    // Template endpoints

    @GetMapping("/mail/template/all")
    public Flux<MailTemplateDto> findAll(){
        return contentService.findAll();
    }

    @GetMapping("/mail/template/{id}")
    public Mono<MailTemplateDto> findTemplateById(@PathVariable("id") String id){
        return contentService.findMailTemplateById(id);
    }

    @GetMapping("/mail/template/user/{id}")
    public Mono<MailTemplateDto> findTemplateByUserId(@PathVariable("id") String id){
        return contentService.findMailTemplateByUserId(id);
    }

    @PostMapping("/mail/template/save")
    public Mono<MailTemplateDto> saveTemplate(@RequestBody MailTemplateDto templateDto){
        return contentService.saveMailTemplate(templateDto);
    }

    @PutMapping("/mail/template/update/{id}")
    public Mono<MailTemplateDto> updateTemplate(@PathVariable("id") String id, @RequestBody MailTemplateDto templateDto){
        return contentService.updateMailTemplate(id, templateDto);
    }

    @DeleteMapping("/mail/template/delete/{id}")
    public Mono<Void> deleteTemplate(@PathVariable("id") String id){
        return contentService.deleteMailTemplateById(id);
    }
}
