package net.space.developer.googlemapcrawler.services;

import net.space.developer.googlemapcrawler.dto.CSVInfoDto;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICSVInfoService {
    Flux<CSVInfoDto> findAll();
    Mono<CSVInfoDto> findById(String id);
    Mono<CSVInfoDto> updateData(CSVInfoDto csvInfoDto, String id);
    Mono<CSVInfoDto> saveData(CSVInfoDto csvInfoDto);
    Mono<Void> deleteData(String id);
    Mono<CSVInfoDto> importFromCSV(Mono<FilePart> filePart);
}
