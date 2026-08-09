package net.space.developer.googlemapcrawler.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.AuthRequest;
import net.space.developer.googlemapcrawler.dto.EmailDto;
import net.space.developer.googlemapcrawler.dto.UserDto;
import net.space.developer.googlemapcrawler.dto.SmtpDto;
import net.space.developer.googlemapcrawler.services.IUserService;
import net.space.developer.googlemapcrawler.utils.RSAKeyUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class AuthController {

    private final IUserService userService;
    private PrivateKey gprk;

    @PostMapping("/auth/register")
    public Mono<ResponseEntity<UserDto>> register(@RequestBody UserDto userDto){
        return userService
                .register(userDto)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/auth/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthRequest request){
        return userService.authenticate(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(401).build()));

    }

    @GetMapping("/smtp-first-config/{id}")
    public Mono<ResponseEntity<SmtpDto>> getSmtpConfig(@PathVariable("id") String id){
        return userService.getSmtpById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/store/stmp-config/{userId}")
    public Mono<ResponseEntity<SmtpDto>> saveSmtpConfig(
            @PathVariable("userId") String userId,
            @RequestBody SmtpDto smtpDto
    ){
        return userService.saveSmtp(userId, smtpDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/delete/smtp/{id}")
    public Mono<ResponseEntity<Void>> deleteSmtp(@PathVariable("id") String id){
        return this.userService.deleteSmtpById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/obtain/public-rsa/key")
    public String getRSAPublicKey(){
        try{
            KeyPair kp = RSAKeyUtil.generateKeyPair();
            PublicKey pk = kp.getPublic();
            this.gprk = kp.getPrivate();
            return RSAKeyUtil.getPublicKeyAsString(pk);
        } catch (NoSuchAlgorithmException e) {
            log.error("rsa.encryption.util.error: {}", e.getMessage());
            return "No se pudo generar la llave publica";
        }
    }

    @PostMapping("/encrypt/data")
    public String encrypt(@RequestBody EmailDto dto){
        try {
            return RSAKeyUtil.encrypt(dto.getPlaceId(), dto.getEmail());
        } catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("encrypt.data.error: {}", e.getMessage());
            return "Error al intentar encryptar datos";
        }
    }

    @PostMapping("/decrypt/data")
    public String decrypt(@RequestBody EmailDto dto){
        try {
            return RSAKeyUtil.decrypt(dto.getPlaceId(), RSAKeyUtil.getPrivateKeyAsString(this.gprk));
        } catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | InvalidKeySpecException |
                 NoSuchAlgorithmException | IllegalBlockSizeException e) {
            log.error("decrypt.data.error: {}", e.getMessage());
            return "No se pudo desencriptar los datos correctamente";
        }
    }

}
