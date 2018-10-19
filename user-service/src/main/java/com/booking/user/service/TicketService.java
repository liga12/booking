package com.booking.user.service;

import com.booking.user.ticket.Ticket;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface TicketService {

    InputStreamResource getFile(String path) throws FileNotFoundException;

    String getTicketUrl(Long placeId, Long paymentClientId, Double cost);

    String createPdf(Ticket ticket);
}
