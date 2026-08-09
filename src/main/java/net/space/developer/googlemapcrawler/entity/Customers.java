package net.space.developer.googlemapcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.dto.Address;
import net.space.developer.googlemapcrawler.enums.CustomerStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("integra.customers")
public class Customers {
    @Id
    private String id;
    @Field
    private String userId;
    @Field
    private String company;
    @Field
    private String web;
    @Field
    private String category;
    @Field
    private Address address;
    @Field
    private String placeId;
    @Field
    private String phone;
    @Field
    private String email;
    @Field
    private String projectId;
    @Field
    private String contactPerson;
    @Field
    private CustomerStatus status;
    @Field
    private String langCode;
    @Field
    private String countryCode;
    @Field
    private ZonedDateTime updated;
    @Field
    private ZonedDateTime registered;
}
