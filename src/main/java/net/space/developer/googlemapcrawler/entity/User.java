package net.space.developer.googlemapcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "integra.users")
public class User {
    @Id
    private String id;
    private String email;
    private String password;

    public static User of(String id, String email, String password){
        return new User(id, email, password);
    }
}
