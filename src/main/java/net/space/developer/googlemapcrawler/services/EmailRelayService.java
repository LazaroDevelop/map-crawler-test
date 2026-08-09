package net.space.developer.googlemapcrawler.services;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.constants.EmailRelayConstants;
import net.space.developer.googlemapcrawler.dto.EmailMessageDto;
import net.space.developer.googlemapcrawler.dto.UserDto;
import net.space.developer.googlemapcrawler.repository.SmtpRepository;
import net.space.developer.googlemapcrawler.repository.UserRepository;
import net.space.developer.googlemapcrawler.utils.AESService;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailRelayService implements IEmailRelayService{

    private final AESService aesService;
    private final SmtpRepository smtpRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> saveUser(UserDto userDto){
        return Mono.just(userDto)
                .map(Utils::userDtoToEntity)
                .flatMap(userRepository::insert)
                .map(Utils::userEntityDto);
    }

    @Override
    public Mono<UserDto> updateUser(String userId, UserDto userDto){
        return userRepository.findById(userId)
                .flatMap(d -> Mono.just(userDto).map(Utils::userDtoToEntity))
                .doOnNext(e -> e.setId(userId))
                .flatMap(userRepository::save)
                .map(Utils::userEntityDto);
    }

    @Override
    public Mono<Void> deleteDataById(String userId){
        return userRepository.deleteById(userId);
    }

    private Session createMailSession(String host, Integer port, String username, String password, String protocol) {
        Properties props = new Properties();

        props.put(EmailRelayConstants.SMTP_AUTH, EmailRelayConstants.TRUE_VALUE);
        if(protocol.equalsIgnoreCase(EmailRelayConstants.SSL)){
            props.put(EmailRelayConstants.SMTP_SSL, EmailRelayConstants.TRUE_VALUE);
        }else {
            props.put(EmailRelayConstants.SMTP_STARTTLS, EmailRelayConstants.TRUE_VALUE);
        }
        props.put(EmailRelayConstants.SMTP_HOST, host);
        props.put(EmailRelayConstants.SMTP_PORT, String.valueOf(port));

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    @Override
    public Mono<Void> sendEmail(String userId, EmailMessageDto messageDto) {
        return smtpRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new Exception(EmailRelayConstants.EMPTY_SMTP_CONFIGURATIONS_EXCEPTION)))
                .flatMap((user -> {

                    try {
                        user.setSmtpPassword(this.aesService.decrypt(user.getSmtpPassword()));
                    } catch (Exception e) {
                        return Mono.error(new Exception(String.format( EmailRelayConstants.ENCRYPT_EXCEPTION, e.getMessage())));
                    }

                    Session session = createMailSession(
                            user.getSmtpHost(),
                            user.getSmtpPort(),
                            user.getSmtpUsername(),
                            user.getSmtpPassword(),
                            user.getSmtpProtocol()
                    );

                    try{
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(user.getSmtpUsername()));
                        message.setRecipients(Message.RecipientType.TO, messageDto.getTo());
                        message.setSubject(messageDto.getSubject());
                        message.setText(messageDto.getText());

                        Transport.send(message);
                    }catch (MessagingException e){
                        return Mono.error(new RuntimeException(String.format(EmailRelayConstants.MAIL_EXCEPTION, e.getMessage())));
                    }

                    return Mono.empty();
                })).then();
    }
}
