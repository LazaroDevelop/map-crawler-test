package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String password;

    public static UserDto of(
            String id,
            String email,
            String password

    ){
        return new UserDto(
                id,
                email,
                password
        );
    }
}
