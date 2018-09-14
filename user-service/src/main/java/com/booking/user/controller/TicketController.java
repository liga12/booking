package com.booking.user.controller;

import com.booking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

    private final UserService userService;

    @GetMapping(value = "/getPdf", produces = "application/pdf")
    public InputStreamResource FileSystemResource(@RequestParam("path") String path,
                                                  HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"demo.pdf\"");
        return new InputStreamResource(new FileInputStream(path));
    }

    @GetMapping("/{placeId}/{paymentClientId}/{cost}")
    public String getTicket(@PathVariable("placeId") Long placeId,
                            @PathVariable("paymentClientId") Long paymentClientId,
                            @PathVariable("cost") Double cost) {
        return userService.getTicketUrl(placeId, paymentClientId, cost);
    }
}
