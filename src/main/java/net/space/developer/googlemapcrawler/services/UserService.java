package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.configuration.JwtUtility;
import net.space.developer.googlemapcrawler.constants.EmailRelayConstants;
import net.space.developer.googlemapcrawler.constants.UserConstants;
import net.space.developer.googlemapcrawler.dto.UserDto;
import net.space.developer.googlemapcrawler.dto.SmtpDto;
import net.space.developer.googlemapcrawler.entity.User;
import net.space.developer.googlemapcrawler.repository.SmtpRepository;
import net.space.developer.googlemapcrawler.repository.UserRepository;
import net.space.developer.googlemapcrawler.utils.AESService;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final JwtUtility jwtUtility;
    private final AESService aesService;
    private final UserRepository userRepository;
    private final SmtpRepository smtpRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Mono<UserDto> register(UserDto userDto) {
        User user = Utils.userDtoToEntity(userDto);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository
                .save(user)
                .map(Utils::userEntityDto);
    }

    @Override
    public Mono<String> authenticate(String email, String password) {
        return this.userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtUtility.generateToken(user.getEmail()));
    }

    @Override
    public Mono<SmtpDto> saveSmtp(String userId, SmtpDto smtpDto) {

        this.userRepository
                .findById(userId)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(UserConstants.USER_NOT_FOUND_EXCEPTION, userId)
                )))
                .log();

        return Mono.just(smtpDto).map(Utils::userSmtpDtoEntity)
                .flatMap(s -> {
                    s.setUserId(userId);
                    try {
                        s.setSmtpPassword(aesService.encrypt(s.getSmtpPassword()));
                    } catch (Exception e) {
                        return Mono.error(new Exception(
                                String.format(EmailRelayConstants.ENCRYPT_EXCEPTION, e.getMessage())
                        ));
                    }
                    return this.smtpRepository.save(s);
                }).map(Utils::smtpEntityDto);
    }

    @Override
    public Mono<SmtpDto> getFirstSmtp(String userId) {
        return this.smtpRepository.findAll()
                .filter(m -> m.getUserId().equals(userId))
                .switchIfEmpty(Mono.error(new Exception(EmailRelayConstants.EMPTY_SMTP_CONFIGURATIONS_EXCEPTION)))
                .map(Utils::smtpEntityDto)
                .next();
    }

    @Override
    public Mono<SmtpDto> getSmtpById(String id) {
        return this.smtpRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(EmailRelayConstants.SMTP_CONFIGURATION_NOT_FOUND_EXCEPTION, id)
                )))
                .map(Utils::smtpEntityDto);
    }

    @Override
    public Mono<Void> deleteSmtpById(String id) {
        return this.smtpRepository.deleteById(id);
    }

    @Override
    public Flux<SmtpDto> getAllSmtp() {
        return this.smtpRepository.findAll()
                .map(Utils::smtpEntityDto);
    }

    @Override
    public Flux<SmtpDto> findSmtpByUserId(String userId) {
        return this.smtpRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new Exception(EmailRelayConstants.EMPTY_SMTP_CONFIGURATIONS_EXCEPTION)))
                .map(Utils::smtpEntityDto);
    }

}
