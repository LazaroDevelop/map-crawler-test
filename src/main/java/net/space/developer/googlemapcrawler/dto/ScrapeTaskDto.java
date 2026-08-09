package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.enums.ActionType;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeTaskDto {
    private String id;
    private String userId;
    private ZonedDateTime start;
    private String category;
    private List<String> searchWords;
    private ActionType action;
    private ZonedDateTime updated;
    private ZonedDateTime registered;
}
