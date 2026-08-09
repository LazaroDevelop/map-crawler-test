package net.space.developer.googlemapcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.enums.CampaignStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("integra.mail_campaign")
public class MailCampaign {
    @Id
    private String id;
    @Field
    private String userId;
    @Field
    private String campaignName;
    @Field
    private ZonedDateTime start;
    @Field
    private String mailTemplateId;
    @Field
    private CampaignStatus status;
    @Field
    private ZonedDateTime updated;
    @Field
    private ZonedDateTime registered;
}
