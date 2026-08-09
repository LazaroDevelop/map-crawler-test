package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.space.developer.googlemapcrawler.entity.Contacts;
import net.space.developer.googlemapcrawler.enums.ContactStatus;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDto {
    private String id;
    private String userId;
    private String company;
    private String website;
    private String category;
    private String address;
    private String placeId;
    private String phone;
    private String email;
    private String projectId;
    private String contactPerson;
    private Boolean called;
    private Boolean mailed;
    private String langCode;
    private String countryCode;
    private ContactStatus status;
    private ZonedDateTime updated;
    private ZonedDateTime registered;

    public static ContactsDto of(
            Contacts contact
    ){
        return new ContactsDto(
                contact.getId(),
                contact.getUserId(),
                contact.getCompany(),
                contact.getWebsite(),
                contact.getCategory(),
                contact.getAddress(),
                contact.getPlaceId(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getProjectId(),
                contact.getContactPerson(),
                contact.getCalled(),
                contact.getMailed(),
                contact.getLangCode(),
                contact.getCountryCode(),
                contact.getStatus(),
                contact.getUpdated(),
                contact.getRegistered()
        );
    }
}
