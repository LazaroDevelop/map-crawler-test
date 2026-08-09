package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.enums.CampaignStatus;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailCampaignDto {
    private String id;
    private String userId;
    private String campaignName;
    private ZonedDateTime start;
    private String mailTemplateId;
    private CampaignStatus status;
    private ZonedDateTime updated;
    private ZonedDateTime registered;
}
