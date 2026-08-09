package net.space.developer.googlemapcrawler.constants;

public final class GoogleMapCrawlerConstants {

    public static final String BASE_URL = "/v1/api/google/crawler";

    public static final String CSV_EXCEPTION_MESSAGE = "Error during CSV creation: %s";
    public static final String COMMA_DELIMITER = ", ";
    public static final String EMAIL_REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    public static final String HTML_ERROR = "Error fetching HTML content";

    private GoogleMapCrawlerConstants(){}
}
