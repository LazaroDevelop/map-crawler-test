package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String placeId;
    private String email;

    public static EmailDto of(String placeId, String email){
        return new EmailDto(placeId, email);
    }
}
