package com.booking.user.ticket;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@PropertySource("classpath:pdf.properties")
public class TicketService {

    @Value("${fileName}")
    private String filePdf;

    @Value("${qaImage}")
    private String qaImage;

    @Value("${footer}")
    private String footer;

    @Value("${rules}")
    private String rules;

    public InputStreamResource getFile(String path){
        InputStreamResource inputStreamResource;
        try( ) {
             inputStreamResource = new InputStreamResource(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStreamResource
    }


    public String createPdf(Ticket ticket) {
        String path = filePdf + createFileName(ticket);
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

            Image img2 = Image.getInstance(footer);
            img2.setAbsolutePosition(0, 150);
            img2.scaleToFit(600, 200);


            document.add(img);
            document.add(img2);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private String createFileName(Ticket ticket) {
        return System.currentTimeMillis() + ticket.getName() + ticket.getSurname() + ".pdf";
    }
}

