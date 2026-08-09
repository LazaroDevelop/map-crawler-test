package net.space.developer.googlemapcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("integra.mail_template_content")
public class MailTemplateContent {
    @Id
    private String id;
    @Field
    private String userId;
    @Field
    private String relayMail;
    @Field
    private String subject;
    @Field
    private String body;
    @Field
    private String greetings;
    @Field
    private String htmlTemplate;
    @Field
    private String signature;
    @Field
    private String disclaimer;
    @Field
    private ZonedDateTime updated;
    @Field
    private ZonedDateTime registered;
}
