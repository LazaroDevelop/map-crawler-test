package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.ContactsDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IContactsService {
    Flux<ContactsDto> findAll();
    Mono<ContactsDto> findById(String id);
    Flux<ContactsDto> saveAllData(List<ContactsDto> contact);
    Mono<ContactsDto> saveData(ContactsDto contact);
    Mono<ContactsDto> updateData(ContactsDto contact, String id);
    Mono<Void> deleteDataById(String id);
}
