package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.UserDto;
import net.space.developer.googlemapcrawler.dto.SmtpDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserDto> register(UserDto userDto);
    Mono<String> authenticate(String email, String password);
    Mono<SmtpDto> saveSmtp(String userId, SmtpDto smtpDto);
    Mono<SmtpDto> getFirstSmtp(String userId);
    Mono<SmtpDto> getSmtpById(String id);
    Mono<Void> deleteSmtpById(String id);
    Flux<SmtpDto> getAllSmtp();
    Flux<SmtpDto> findSmtpByUserId(String userId);
}
