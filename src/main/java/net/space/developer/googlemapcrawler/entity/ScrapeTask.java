package net.space.developer.googlemapcrawler.entity;

import java.time.ZonedDateTime;
import java.util.List;

import net.space.developer.googlemapcrawler.dto.ScrapeTaskDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.enums.ActionType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("integra.scrapetask")
public class ScrapeTask {
    @Id
    private String id;
    @Field
    private String userId;
    @Field
    private ZonedDateTime start;
    @Field
    private String category;
    @Field
    private List<String> searchWords;
    @Field
    private ActionType action;
    @Field
    private ZonedDateTime updated;
    @Field
    private ZonedDateTime registered;

    public static ScrapeTask of(
            ScrapeTaskDto dto
    ){
        return new ScrapeTask(
                dto.getId(),
                dto.getUserId(),
                dto.getStart(),
                dto.getCategory(),
                dto.getSearchWords(),
                dto.getAction(),
                dto.getUpdated(),
                dto.getRegistered()
        );
    }
}
