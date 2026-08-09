package net.space.developer.googlemapcrawler.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.constants.ContactsConstants;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.CSVInfoDto;
import net.space.developer.googlemapcrawler.dto.ContactsDto;
import net.space.developer.googlemapcrawler.services.ICSVInfoService;
import net.space.developer.googlemapcrawler.services.IContactsService;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class ContactsController {

    private final IContactsService dataService;
    private final ICSVInfoService infoService;

    //Data persistence

    @GetMapping("/data/all")
    public Flux<ContactsDto> findAll() {
        return this.dataService.findAll();
    }

    @GetMapping("/data/{id}")
    public Mono<ContactsDto> findDataById(@PathVariable("id") String id) {
        return this.dataService.findById(id);
    }

    @PostMapping("/data/store/all")
    public Flux<ContactsDto> saveAllData(@RequestBody List<ContactsDto> dataDtoFlux) {
        return this.dataService.saveAllData(dataDtoFlux);
    }

    @PostMapping("/data/store")
    public Mono<ContactsDto> saveData(@RequestBody ContactsDto dataDto) {
        return this.dataService.saveData(dataDto);
    }

    @PutMapping("/data/update/{id}")
    public Mono<ContactsDto> updateData(@PathVariable("id") String id, @RequestBody ContactsDto dataDtoMono) {
        return this.dataService.updateData(dataDtoMono, id);
    }

    @DeleteMapping("/data/delete/{id}")
    public Mono<Void> deleteDate(@PathVariable("id") String id) {
        return this.dataService.deleteDataById(id);
    }

    // Importing CSV
    @PostMapping("/upload/csv")
    public Mono<CSVInfoDto> importCSV(
            @RequestPart("file") Mono<FilePart> file
    ){
        return this.infoService.importFromCSV(file);
    }

    @GetMapping("/all/stored/csv")
    public Flux<CSVInfoDto> getAllStoredCsv(){
        return this.infoService.findAll();
    }

    //Exporting CSV

    @PostMapping("/export/csv")
    public ResponseEntity<byte[]> exportCSV(@RequestBody List<ContactsDto> businessDataDtoFlux) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(ContactsConstants.FILE_NAME)
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(Utils.createCSVFromContacts(businessDataDtoFlux));
    }
}
