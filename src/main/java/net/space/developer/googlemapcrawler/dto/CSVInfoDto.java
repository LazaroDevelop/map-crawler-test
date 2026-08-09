package net.space.developer.googlemapcrawler.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CSVInfoDto {
    private String id;
    private List<String> headers;
    private List<String> values;
}
