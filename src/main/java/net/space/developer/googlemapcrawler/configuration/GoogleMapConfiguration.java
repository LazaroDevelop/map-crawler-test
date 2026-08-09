package net.space.developer.googlemapcrawler.configuration;

import com.google.maps.GeoApiContext;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapConfiguration {

    @Value("${google.map.api.key}")
    private String apiKey;

    @Bean
    public GeoApiContext getApiContext(){
        return new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .maxRetries(3)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryTimeout(0, TimeUnit.SECONDS)
                .queryRateLimit(100)
                .build();
    }
}
