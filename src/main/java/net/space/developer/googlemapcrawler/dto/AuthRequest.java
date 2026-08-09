package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;

    public static AuthRequest of(String email, String password){
        return new AuthRequest(email, password);
    }
}
