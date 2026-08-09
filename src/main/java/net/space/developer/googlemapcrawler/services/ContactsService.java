package net.space.developer.googlemapcrawler.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.constants.ContactsConstants;
import net.space.developer.googlemapcrawler.dto.ContactsDto;
import net.space.developer.googlemapcrawler.repository.ContactsRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ContactsService implements IContactsService {

    private final ContactsRepository dataRepository;

    @Override
    public Flux<ContactsDto> findAll() {
        return dataRepository.findAll()
                .map(Utils::entityToDto);
    }

    @Override
    public Flux<ContactsDto> saveAllData(List<ContactsDto> contactsDtos) {
        return Flux.fromIterable(contactsDtos)
                .map(Utils::dtoToEntity)
                .flatMap(dataRepository::save)
                .map(Utils::entityToDto);
    }

    @Override
    public Mono<ContactsDto> findById(String id) {
        return dataRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(ContactsConstants.CONTACT_NOT_FOUND, id)))
                )
                .map(Utils::entityToDto);
    }

    @Override
    public Mono<ContactsDto> saveData(ContactsDto contact) {
        return Mono.just(contact)
                .map(Utils::dtoToEntity)
                .flatMap(dataRepository::insert)
                .map(Utils::entityToDto);
    }

    @Override
    public Mono<ContactsDto> updateData(ContactsDto contact, String id) {
        return dataRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(ContactsConstants.CONTACT_NOT_FOUND, id)))
                )
                .flatMap(i -> {
                    BeanUtils.copyProperties(contact, i);
                    i.setId(id);
                    return dataRepository.save(i);
                })
                .map(Utils::entityToDto);
    }

    @Override
    public Mono<Void> deleteDataById(String id) {
        return dataRepository.deleteById(id);
    }
}
