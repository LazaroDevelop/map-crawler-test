package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String fullAddress;

    public static Address of(
            String street,
            String postalCode,
            String city,
            String country,
            String fullAddress){
        return new Address(street, postalCode, city, country, fullAddress);
    }
}
