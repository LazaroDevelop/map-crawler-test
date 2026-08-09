package net.space.developer.googlemapcrawler.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.dto.SmtpDto;
import net.space.developer.googlemapcrawler.enums.SmtpType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "integra.smtp")
public class Smtp {
    @Id
    private String id;
    @Field
    private String userId;
    @Field
    private String smtpHost;
    @Field
    private int smtpPort;
    @Field
    private String smtpProtocol;
    @Field
    private String smtpUsername;
    @Field
    private String smtpPassword;
    @Field
    private SmtpType type;

    public static Smtp of(
            SmtpDto smtpDto
    ){
        return new Smtp(
                smtpDto.getId(),
                smtpDto.getUserId(),
                smtpDto.getSmtpHost(),
                smtpDto.getSmtpPort(),
                smtpDto.getSmtpProtocol(),
                smtpDto.getSmtpUsername(),
                smtpDto.getSmtpPassword(),
                SmtpType.valueOf(smtpDto.getSmtpType())
        );
    }
}
