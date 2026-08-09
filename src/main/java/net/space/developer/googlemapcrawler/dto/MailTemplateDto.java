package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplateDto {
    private String id;
    private String userId;
    private String relayMail;
    private String subject;
    private String body;
    private String greetings;
    private String htmlTemplate;
    private String signature;
    private String disclaimer;
    private ZonedDateTime updated;
    private ZonedDateTime registered;
}
