package net.space.developer.googlemapcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("integra.html_design")
public class HtmlDesign {
    @Id
    private String id;
    @Field
    private String userId;
    @Field
    private String name;
    @Field
    private String design;
    @Field
    private String htmlTemplate;
}
