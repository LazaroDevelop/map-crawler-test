package net.space.developer.googlemapcrawler.controller;

import com.google.maps.ImageResult;
import com.google.maps.model.*;
import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.ContactPaginator;
import net.space.developer.googlemapcrawler.services.IGoogleCrawlerService;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class GoogleCrawlerController {

    private final IGoogleCrawlerService googleCrawlerService;

    //Searching information

    @GetMapping("/search")
    public Mono<PlacesSearchResponse> searchByPlaces(
            @RequestParam String query,
            @RequestParam(required = false) String nextPageToken
    ) {
        return googleCrawlerService.searchPlaces(query, nextPageToken);
    }

    @PostMapping("/search/by/query")
    public Mono<ContactPaginator> searchByQueryMono(
            @RequestBody String query,
            @RequestParam(required = false) String next
    ) {
        return googleCrawlerService.searchContacts(query, next);
    }

    @PostMapping("/search/by/place-type")
    public Mono<ContactPaginator> searchContactsByPlaceType(
            @RequestBody String placeType,
            @RequestParam(required = false) String next
    ){
        return googleCrawlerService.searchContactsByPlaceType(placeType, next);
    }
    

    @GetMapping("/search/location")
    public Mono<PlacesSearchResponse> searchPlacesByLocation(
            @RequestParam String query,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam int radius,
            @RequestParam(required = false) String nextPageToken
    ) {
        return googleCrawlerService.searchPlacesByLocation(query, lat, lng, radius, nextPageToken);
    }

    @GetMapping("/search/places-type")
    public Mono<PlacesSearchResponse> searchPlacesByPlaceType(
            @RequestParam String placeType,
            @RequestParam(required = false) String nextPageToken
    ) {
        return googleCrawlerService.searchPlacesByPlaceType(placeType, nextPageToken);
    }


    @GetMapping("/search/auto")
    public Mono<AutocompletePrediction[]> searchByPlacesAutocompleteQuery(@RequestParam String query) {
        return googleCrawlerService.searchPlacesWithAutocomplete(query);
    }

    @GetMapping("/search/details/{placeId}")
    public Mono<PlaceDetails> searchPlacesDetails(@PathVariable("placeId") String placeId) {
        return googleCrawlerService.searchPlaceDetails(placeId);
    }

    @GetMapping("/search/photo")
    public Mono<ImageResult> searchPhotoByReference(
            @RequestParam String reference,
            @RequestParam int maxWidth,
            @RequestParam int maxHeight
    ) {
        return googleCrawlerService.searchPlacesPhotos(reference, maxWidth, maxHeight);
    }

    @GetMapping("/search/text_or_phone_number")
    public Mono<FindPlaceFromText> searchByTextOrPhoneNumber(
            @RequestParam String input,
            @RequestParam boolean isTextSearch
    ) {
        return googleCrawlerService.searchPlaceFromText(input, isTextSearch);
    }

    @PostMapping("/search/mail-list")
    public Mono<String> searchMailList(@RequestBody String website) {
        return googleCrawlerService.performMailList(website);
    }

    @GetMapping("/places-type")
    public Mono<PlaceType[]> getPlacesType() {
        return Mono.just(PlaceType.values());
    }
}
