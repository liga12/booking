package com.booking.user.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public interface TicketService {

    InputStreamResource getFile(String path) throws FileNotFoundException;

    String getTicketUrl(Long placeId, Long paymentClientId, Double cost);
}
