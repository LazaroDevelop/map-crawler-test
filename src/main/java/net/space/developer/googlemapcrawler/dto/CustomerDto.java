package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.enums.CustomerStatus;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String id;
    private String userId;
    private String company;
    private String web;
    private String category;
    private Address address;
    private String placeId;
    private String phone;
    private String email;
    private String projectId;
    private String contactPerson;
    private CustomerStatus status;
    private String langCode;
    private String countryCode;
    private ZonedDateTime updated;
    private ZonedDateTime registered;
}
