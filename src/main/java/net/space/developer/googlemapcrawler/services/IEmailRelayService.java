package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.EmailMessageDto;
import net.space.developer.googlemapcrawler.dto.UserDto;
import reactor.core.publisher.Mono;

public interface IEmailRelayService {
    Mono<UserDto> saveUser(UserDto userDto);
    Mono<UserDto> updateUser(String userId, UserDto userDto);
    Mono<Void> deleteDataById(String userId);
    Mono<Void> sendEmail(String userId, EmailMessageDto messageDto);
}
