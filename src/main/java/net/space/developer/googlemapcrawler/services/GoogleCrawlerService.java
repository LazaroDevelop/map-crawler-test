package net.space.developer.googlemapcrawler.services;

import com.google.maps.*;
import com.google.maps.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.ContactPaginator;
import net.space.developer.googlemapcrawler.dto.ContactsDto;
import net.space.developer.googlemapcrawler.dto.EmailDto;
import net.space.developer.googlemapcrawler.utils.Utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleCrawlerService implements IGoogleCrawlerService {

    private final Map<String, String> codeSite = new LinkedHashMap<>();
    private final GeoApiContext geoApiContext;
    private final WebClient webClient = WebClient.create();

    @Override
    public Mono<ContactPaginator> searchContacts(String query, String nextPageToken) {
        TextSearchRequest tsr = new TextSearchRequest(geoApiContext);
        tsr.query(query);
        if (nextPageToken != null) {
            tsr.pageToken(nextPageToken);
        }
        return Mono.from(Mono.fromCallable(tsr::await)
                .flatMapMany(r -> {
                    String pageToken = r.nextPageToken;
                    return Flux.fromArray(r.results)
                            .flatMap(result ->
                                    Mono.fromCallable(() -> PlacesApi.placeDetails(geoApiContext, result.placeId).await())
                                            .map(v -> Utils.toContactsDto(v))
                                            .onErrorResume(error -> Mono.empty())
                            ).collectList()
                            .map(contacts -> ContactPaginator.of(contacts, pageToken));
                }));
    }

    @Override
    public Mono<ContactPaginator> searchContactsByPlaceType(String placeType, String nextPageToken) {
        TextSearchRequest tsr = new TextSearchRequest(geoApiContext);
        tsr.type(PlaceType.valueOf(placeType));
        if (nextPageToken != null) {
            tsr.pageToken(nextPageToken);
        }
        return Mono.from(Mono.fromCallable(tsr::await)
                .flatMapMany(r -> {
                    String pageToken = r.nextPageToken;
                    return Flux.fromArray(r.results)
                            .flatMap(result ->
                                    Mono.fromCallable(() -> PlacesApi.placeDetails(geoApiContext, result.placeId).await())
                                            .map(v -> Utils.toContactsDto(v))
                                            .onErrorResume(error -> Mono.empty())
                            ).collectList()
                            .map(contacts -> ContactPaginator.of(contacts, pageToken));
                }));
    }


    @Override
    public Mono<PlacesSearchResponse> searchPlaces(String query, String nextPageToken) {
        TextSearchRequest tsr = new TextSearchRequest(geoApiContext);
        tsr.query(query);
        if (nextPageToken != null) {
            tsr.pageToken(nextPageToken);
        }
        return Mono.fromCallable(tsr::await);
    }

    @Override
    public Mono<PlacesSearchResponse> searchPlacesByPlaceType(String placeType, String nextPageToken) {
        TextSearchRequest tsr = new TextSearchRequest(geoApiContext);
        tsr.type(PlaceType.valueOf(placeType));
        if (nextPageToken != null) {
            tsr.pageToken(nextPageToken);
        }
        return Mono.fromCallable(tsr::await);
    }

    @Override
    public Mono<PlacesSearchResponse> searchPlacesByLocation(
            String query,
            double lat,
            double lng,
            int radius,
            String nextPageToken) {
        TextSearchRequest tsr = new TextSearchRequest(geoApiContext);
        tsr.query(query);
        tsr.location(new LatLng(lat, lng));
        tsr.radius(radius);
        if (nextPageToken != null) {
            tsr.pageToken(nextPageToken);
        }
        return Mono.fromCallable(tsr::await);
    }

    @Override
    public Mono<AutocompletePrediction[]> searchPlacesWithAutocomplete(String query) {
        return Mono.fromCallable(() -> PlacesApi.queryAutocomplete(geoApiContext, query).await());
    }

    @Override
    public Mono<PlaceDetails> searchPlaceDetails(String placeId) {
        return Mono.fromCallable(() -> PlacesApi.placeDetails(geoApiContext, placeId).await())
                .doOnSuccess(result -> {
                    if (result.website != null) {
                        this.codeSite.put(
                                placeId,
                                result.website.toString());
                    }
                });
    }

    @Override
    public Mono<ImageResult> searchPlacesPhotos(String photoReference, int maxWidth, int maxHeight) {
        return Mono.fromCallable(() -> PlacesApi.photo(geoApiContext, photoReference)
                .maxWidth(maxWidth)
                .maxHeight(maxHeight)
                .await());
    }

    @Override
    public Mono<FindPlaceFromText> searchPlaceFromText(String input, boolean isTextSearch) {
        FindPlaceFromTextRequest fpfr = new FindPlaceFromTextRequest(geoApiContext);
        fpfr.input(input);
        if (isTextSearch) {
            fpfr.inputType(FindPlaceFromTextRequest.InputType.TEXT_QUERY);
        } else {
            fpfr.inputType(FindPlaceFromTextRequest.InputType.PHONE_NUMBER);
        }
        return Mono.fromCallable(fpfr::await);
    }

    @Override
    public Mono<String> performMailList(String website) {
            return fetchHtmlContent(website)
                    .flatMap(this::extractEmailFromHtml);
    }

    private Mono<String> fetchHtmlContent(String url) {
        return webClient.get()
                .uri(URI.create(url))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> Mono.error(new RuntimeException(GoogleMapCrawlerConstants.HTML_ERROR)))
                .bodyToMono(String.class);
    }

    private Mono<String> extractEmailFromHtml(String html) {
        Pattern pattern = Pattern.compile(GoogleMapCrawlerConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(html);
        return matcher.find() ? Mono.just(matcher.group()) : Mono.empty();
    }
}
