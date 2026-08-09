package net.space.developer.googlemapcrawler.services;

import com.google.maps.ImageResult;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import net.space.developer.googlemapcrawler.dto.ContactPaginator;
import reactor.core.publisher.Mono;

public interface IGoogleCrawlerService {
    Mono<ContactPaginator> searchContacts(String query, String nextPageToken);
    Mono<ContactPaginator> searchContactsByPlaceType(String placeType, String nextPageToken);
    Mono<PlacesSearchResponse> searchPlaces(String query, String nextPageToken);
    Mono<PlacesSearchResponse> searchPlacesByLocation(String query, double lat, double lng, int radius, String nextPageToken);
    Mono<PlacesSearchResponse> searchPlacesByPlaceType(String placeType, String nextPageToken);
    Mono<AutocompletePrediction[]> searchPlacesWithAutocomplete(String query);
    Mono<PlaceDetails> searchPlaceDetails(String placeId);
    Mono<ImageResult> searchPlacesPhotos(String photoReference, int maxWith, int maxHeight);
    Mono<FindPlaceFromText> searchPlaceFromText(String input, boolean isTextSearch);
    Mono<String> performMailList(String placeId);
}
