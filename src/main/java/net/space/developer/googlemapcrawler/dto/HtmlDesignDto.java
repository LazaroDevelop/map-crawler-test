package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HtmlDesignDto {
    private String id;
    private String userId;
    private String name;
    private String design;
    private String htmlTemplate;
}
