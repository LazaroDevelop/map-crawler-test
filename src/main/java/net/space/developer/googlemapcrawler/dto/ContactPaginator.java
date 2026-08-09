package net.space.developer.googlemapcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactPaginator {
    private List<ContactsDto> contacts;
    private String nextPageToken;

    public static ContactPaginator of(
            List<ContactsDto> contacts,
            String nextPageToken
    ){
        return new ContactPaginator(contacts, nextPageToken);
    }
}
