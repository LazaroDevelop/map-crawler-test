package net.space.developer.googlemapcrawler.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmtpDto {
    private String id;
    private String userId;
    private String smtpHost;
    private int smtpPort;
    private String smtpProtocol;
    private String smtpUsername;
    private String smtpPassword;
    private String smtpType;
}
