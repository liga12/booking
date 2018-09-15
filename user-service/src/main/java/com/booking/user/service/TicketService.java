package com.booking.user.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    InputStreamResource getFile(String path);

    String getTicketUrl(Long placeId, Long paymentClientId, Double cost);
}
