package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.dto.CSVInfoDto;
import net.space.developer.googlemapcrawler.repository.CSVInfoRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CSVInfoService implements ICSVInfoService {

    private final CSVInfoRepository dataRepository;

    @Override
    public Flux<CSVInfoDto> findAll() {
        return dataRepository.findAll()
                .map(Utils::toCSVInfoDto);
    }

    @Override
    public Mono<CSVInfoDto> findById(String id) {
        return dataRepository.findById(id)
                .map(Utils::toCSVInfoDto);
    }

    @Override
    public Mono<CSVInfoDto> updateData(CSVInfoDto csvInfoDto, String id) {
        return dataRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format("CSV Info not found with id %s", id))))
                .map(data -> {
                    data.setHeaders(csvInfoDto.getHeaders());
                    data.setValues(csvInfoDto.getValues());
                    return data;
                })
                .flatMap(dataRepository::save)
                .map(Utils::toCSVInfoDto);
    }

    @Override
    public Mono<CSVInfoDto> saveData(CSVInfoDto csvInfoDto) {
        return dataRepository
                .save(Utils.toCSVInfoEntity(csvInfoDto))
                .map(Utils::toCSVInfoDto);
    }

    @Override
    public Mono<Void> deleteData(String id) {
        return dataRepository.deleteById(id);
    }

    @Override
    public Mono<CSVInfoDto> importFromCSV(Mono<FilePart> filePart) {
        return filePart.flatMap(
            file -> file.content()
            .mapNotNull(m -> Utils.importCSV(m.asInputStream(true)))
            .map(Utils::toCSVInfoEntity)
                    .flatMap(dataRepository::insert)
                    .map(Utils::toCSVInfoDto).next()
        );
    }

}
