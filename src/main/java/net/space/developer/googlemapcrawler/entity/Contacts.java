package net.space.developer.googlemapcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import net.space.developer.googlemapcrawler.dto.ContactsDto;
import net.space.developer.googlemapcrawler.enums.ContactStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.beans.ConstructorProperties;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("integra.contacts")
public class Contacts {
    @Id
    private String id;
    @Field
    private String userId; // user identifier
    @Field
    private String company; // company name
    @Field
    private String website; // web url
    @Field
    private String category; // category
    @Field
    private String address; // company address or user address
    @Field
    private String placeId; // place identifier
    @Field
    private String phone; // company phone or user phone
    @Field
    private String email; // email
    @Field
    private String projectId; // project identifier
    @Field
    private String contactPerson; // company owner or person to contract
    @Field
    private Boolean called; // company was called by phone
    @Field
    private Boolean mailed; // email was sent to company
    @Field
    private String langCode; // languaje code
    @Field
    private String countryCode; // country code
    @Field
    private ContactStatus status;
    @Field
    private ZonedDateTime updated; // last time updated
    @Field
    private ZonedDateTime registered; // first time registered

    public static Contacts of(
            ContactsDto contactsDto
    ) {
        return new Contacts(
                contactsDto.getId(),
                contactsDto.getUserId(),
                contactsDto.getCompany(),
                contactsDto.getWebsite(),
                contactsDto.getCategory(),
                contactsDto.getAddress(),
                contactsDto.getPlaceId(),
                contactsDto.getPhone(),
                contactsDto.getEmail(),
                contactsDto.getProjectId(),
                contactsDto.getContactPerson(),
                contactsDto.getCalled(),
                contactsDto.getMailed(),
                contactsDto.getLangCode(),
                contactsDto.getCountryCode(),
                contactsDto.getStatus(),
                contactsDto.getUpdated(),
                contactsDto.getRegistered()
        );
    }
}
