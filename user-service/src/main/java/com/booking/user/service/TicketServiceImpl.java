package com.booking.user.service;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.user.exception.*;
import com.booking.user.service.feign.EventService;
import com.booking.user.ticket.Ticket;
import com.booking.user.transpor.dto.UserOutcomeDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@PropertySource("classpath:pdf.properties")
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UserService userService;

    private final Environment environment;

    private final EventService eventService;

    @Value("${pdf.folder}")
    private String pdfFolder;

    @Value("${pdf.image.qa}")
    private String qaImage;

    @Value("${pdf.image.footer}")
    private String footerImage;

    @Value("${rules}")
    private String rules;

    @Override
    public InputStreamResource getFile(String path) throws FileNotFoundException {
        return new InputStreamResource(new FileInputStream(path));
    }

    @Override
    public String getTicketUrl(Long placeId, Long paymentClientId, Double cost) {
        String controllerMethod = "tickets/getPdf?path=";
        String hostAndPort = "http://localhost:" + environment.getProperty("server.port") + "/";
        Ticket ticket = new Ticket();
        PlaceOutcomeDto placeDto = getPlace(placeId);
        AbstractEventOutcomeDto event = getEvent(placeDto.getEvent());
        String organizationPhone = getOrganizationPhone(event.getOrganization());
        UserOutcomeDto user = userService.getUserByPaymentId(paymentClientId);
        ticket.setName(user.getName());
        ticket.setSurname(user.getSurname());
        ticket.setPlaceNumber(placeDto.getNumber());
        ticket.setPlaceRow(placeDto.getRow());
        ticket.setEvent(event.getName());
        ticket.setDate(

                longToLocalDateTime(event.getDate()));
        ticket.setCost(cost);
        ticket.setOrganizationPhone(organizationPhone);
        return hostAndPort + controllerMethod + createPdf(ticket);
    }

    @Override
    public String createPdf(Ticket ticket) {
        if (ticket == null) {
            throw new NotCorrectTicketDateException();
        }
        createPdfFolderAndFiles();
        String path = pdfFolder + createFileName(ticket);
        Document document = new Document();
        try {
            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(path)
            );
            document.open();
            document.add(new Paragraph("Name: " + ticket.getName()));
            document.add(new Paragraph("Surname: " + ticket.getSurname()));
            document.add(new Paragraph("Event: " + ticket.getEvent()));
            document.add(new Paragraph("Place number: " + ticket.getPlaceNumber()));
            document.add(new Paragraph("Place row: " + ticket.getPlaceRow()));
            document.add(new Paragraph("Date: " + ticket.getDate()));
            document.add(new Paragraph("Cost: " + ticket.getCost()));
            document.add(new Paragraph("Contact phone: " + ticket.getOrganizationPhone()));
            document.add(new Paragraph(rules));
            Image img = Image.getInstance(qaImage);
            img.setAbsolutePosition(350, 630);
            img.scaleToFit(200, 200);
            Image img2 = Image.getInstance(footerImage);
            img2.setAbsolutePosition(0, 150);
            img2.scaleToFit(600, 200);
            document.add(img);
            document.add(img2);
            document.close();
        } catch (DocumentException |
                IOException e) {
            throw new PdfDirectoryNotFoundException();
        }
        return path;
    }

    private String createFileName(Ticket ticket) {
        return System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";
    }

    private PlaceOutcomeDto getPlace(Long placeId) {
        if (placeId == null || !eventService.existsBuyPlace(placeId)) {
            throw new PlaceNotFoundException();
        }
        return eventService.getPlace(placeId);
    }

    private AbstractEventOutcomeDto getEvent(Long eventId) {
        if (eventId == null || !eventService.existsEvent(eventId)) {
            throw new EventNotFoundException();
        }
        return eventService.getEvent(eventId);
    }

    private String getOrganizationPhone(Long organizationId) {
        if (organizationId == null || !eventService.existsOrganization(organizationId)) {
            throw new OrganizationNotFoundException();
        }
        return eventService.getOrganizationPhone(organizationId);
    }

    private String longToLocalDateTime(Long time) {
        return formatDate(
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(time * 1000),
                        ZoneId.systemDefault()
                )
        );
    }

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

    private void createPdfFolderAndFiles() {
        try {
            Files.createDirectories(Paths.get("./pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedInputStream footerOriginal = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("pdf/2.png"));
             BufferedInputStream qrOriginal = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("pdf/qr.png"));
             BufferedOutputStream footerCopy = new BufferedOutputStream(new FileOutputStream(new File(footerImage)));
             BufferedOutputStream qrCopy = new BufferedOutputStream(new FileOutputStream(new File(qaImage)))
        ) {
            int b = 0;
            while (b != -1) {
                b = footerOriginal.read();
                footerCopy.write(b);
            }
            b = 0;
            while (b != -1) {
                b = qrOriginal.read();
                qrCopy.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

