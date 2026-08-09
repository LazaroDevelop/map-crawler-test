package net.space.developer.googlemapcrawler.utils;

import com.google.maps.model.AddressType;
import com.google.maps.model.PlaceDetails;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRuntimeException;
import net.space.developer.googlemapcrawler.constants.ContactsConstants;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.*;
import net.space.developer.googlemapcrawler.entity.*;
import net.space.developer.googlemapcrawler.enums.ContactStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static final String CSV_TYPE1 = "text/csv";
    public static final String CSV_TYPE2 = "application/vnd.ms-excel";
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date stringToDate(String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Contacts mappers

    public static Contacts dtoToEntity(ContactsDto dto) {
        Contacts businessData = new Contacts();
        BeanUtils.copyProperties(dto, businessData);
        return businessData;
    }

    public static ContactsDto entityToDto(Contacts data) {
        ContactsDto dto = new ContactsDto();
        BeanUtils.copyProperties(data, dto);
        return dto;
    }

    //Users mappers

    public static User userDtoToEntity(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static UserDto userEntityDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    //Smtp mappers

    public static Smtp userSmtpDtoEntity(SmtpDto dto) {
        Smtp smtp = new Smtp();
        BeanUtils.copyProperties(dto, smtp);
        return smtp;
    }

    public static SmtpDto smtpEntityDto(Smtp smtp) {
        SmtpDto dto = new SmtpDto();
        BeanUtils.copyProperties(smtp, dto);
        return dto;
    }


    // Scrape task

    public static ScrapeTask scrapeTaskDtoToEntity(ScrapeTaskDto scrapeTaskDto) {
        ScrapeTask scrapeTask = new ScrapeTask();
        BeanUtils.copyProperties(scrapeTaskDto, scrapeTask);
        return scrapeTask;
    }

    public static ScrapeTaskDto scrapeTaskEntityToDto(ScrapeTask scrapeTask) {
        ScrapeTaskDto scrapeTaskDto = new ScrapeTaskDto();
        BeanUtils.copyProperties(scrapeTask, scrapeTaskDto);
        return scrapeTaskDto;
    }

    // Customer mappers

    public static CustomerDto customerEntityToDto(Customers customers) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customers, customerDto);
        return customerDto;
    }

    public static Customers customersDtoToEntity(CustomerDto customerDto) {
        Customers customers = new Customers();
        BeanUtils.copyProperties(customerDto, customers);
        return customers;
    }

    // Mail Campaign mappers

    public static MailCampaignDto campaignEntityToDto(MailCampaign mailCampaign) {
        MailCampaignDto mailCampaignDto = new MailCampaignDto();
        BeanUtils.copyProperties(mailCampaign, mailCampaignDto);
        return mailCampaignDto;
    }

    public static MailCampaign campaignDtoToEntity(MailCampaignDto mailCampaignDto) {
        MailCampaign mailCampaign = new MailCampaign();
        BeanUtils.copyProperties(mailCampaignDto, mailCampaign);
        return mailCampaign;
    }

    //Mail Template Content mappers

    public static MailTemplateDto mailTemplateEntityToDto(MailTemplateContent mailTemplateContent) {
        MailTemplateDto mailTemplateDto = new MailTemplateDto();
        BeanUtils.copyProperties(mailTemplateContent, mailTemplateDto);
        return mailTemplateDto;
    }

    public static MailTemplateContent mailTemplateDtoToEntity(MailTemplateDto mailTemplateDto) {
        MailTemplateContent templateContent = new MailTemplateContent();
        BeanUtils.copyProperties(mailTemplateDto, templateContent);
        return templateContent;
    }

    //Place Details to ContactsDto

    public static ContactsDto toContactsDto(PlaceDetails details) {
        return ContactsDto.builder()
                .placeId(details.placeId)
                .company(details.name)
                .address(details.formattedAddress)
                .called(false)
                .mailed(false)
                .category(Arrays.stream(details.types).map(AddressType::toString).collect(Collectors.joining(", ")))
                .phone(details.internationalPhoneNumber == null ? "-" : details.internationalPhoneNumber)
                .website(details.website != null ? details.website.toString() : "")
                .registered(ZonedDateTime.now())
                .updated(ZonedDateTime.now())
                .status(ContactStatus.CONTACT)
                .build();

    }

    // HTML Design Dto to Entity
    public static HtmlDesign toHtmlDesignEntity(HtmlDesignDto htmlDesignDto) {
        return HtmlDesign.builder()
                .id(htmlDesignDto.getId())
                .userId(htmlDesignDto.getUserId())
                .name(htmlDesignDto.getName())
                .design(htmlDesignDto.getDesign())
                .htmlTemplate(htmlDesignDto.getHtmlTemplate())
                .build();
    }

    // HTML Design Entity to Dto
    public static HtmlDesignDto toHtmlDesignDto(HtmlDesign htmlDesign) {
        return HtmlDesignDto.builder()
                .id(htmlDesign.getId())
                .userId(htmlDesign.getUserId())
                .name(htmlDesign.getName())
                .design(htmlDesign.getDesign())
                .htmlTemplate(htmlDesign.getHtmlTemplate())
                .build();
    }

    // CSV Dto to Entity
    public static CSVInfo toCSVInfoEntity(CSVInfoDto infoDto) {
        return CSVInfo.builder()
                .id(infoDto.getId())
                .headers(infoDto.getHeaders())
                .values(infoDto.getValues())
                .build();
    }

    // CSV Entity to Dto
    public static CSVInfoDto toCSVInfoDto(CSVInfo csvInfo) {
        return CSVInfoDto.builder()
                .id(csvInfo.getId())
                .headers(csvInfo.getHeaders())
                .values(csvInfo.getValues())
                .build();
    }

    // CSV Formatter checker
    public static boolean hasCsvFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return (CSV_TYPE1.equals(contentType) || CSV_TYPE2.equals(contentType));
    }

    // CSV Importer
    public static CSVInfoDto importCSV(InputStream is) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                CSVReader reader = new CSVReader(bufferedReader)
        ) {


            // Read all rows
            List<String[]> rows = reader.readAll();

            // Extract headers
            List<String> headers = List.of(rows.get(0));

            // Extract data
            List<String> results = rows.stream()
                    .skip(1)
                    .map(row -> String.join(" ", row))
                    .toList();


            log.info("Results: {}", results);


            return CSVInfoDto.builder()
                    .headers(headers)
                    .values(results)
                    .build();
        } catch (IOException | CsvException ioException) {
            return null;
        }
    }


    // CSV Exporter

    public static byte[] createCSVFromContacts(List<ContactsDto> dataDtos) {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8)
        ) {
            //Define the csv writer
            CSVWriter writer = new CSVWriter(osw);

            //Writing file headers
            writer.writeNext(ContactsConstants.HEADERS);

            //Writing file data

            if (dataDtos != null) {
                for (var b : dataDtos) {
                    String[] data = {
                            b.getCompany(),
                            b.getWebsite(),
                            b.getCategory(),
                            b.getAddress(),
                            b.getPhone(),
                            b.getEmail(),
                            b.getContactPerson(),
                            b.getLangCode(),
                            b.getCountryCode()
                    };

                    writer.writeNext(data);
                }
            }

            writer.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CsvRuntimeException(String.format(
                    GoogleMapCrawlerConstants.CSV_EXCEPTION_MESSAGE,
                    e.getMessage()
            ));
        }
    }

    private Utils() {
    }
}
