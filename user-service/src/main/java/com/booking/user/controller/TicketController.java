package com.booking.user.controller;

import com.booking.user.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping(value = "/getPdf", produces = "application/pdf")
    public InputStreamResource getPdf(@RequestParam("path") String path,
                                      HttpServletResponse response) throws FileNotFoundException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"demo.pdf\"");
        return ticketService.getFile(path);
    }

    @GetMapping("/{placeId}/{paymentClientId}/{cost}")
    public String getTicket(@PathVariable("placeId") Long placeId,
                            @PathVariable("paymentClientId") Long paymentClientId,
                            @PathVariable("cost") Double cost) {
        return ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }
}
