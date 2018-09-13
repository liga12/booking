package com.booking.user.controller;

import com.booking.user.api.UserApi;
import com.booking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final UserService userService;

    @Override
    public boolean existsCustomer(@PathVariable String customerId) {
        return userService.existsCustomer(customerId);
    }

    @Override
    public boolean existCustomerByPaymentId(@PathVariable Long paymentId) {
        return userService.existsCustomerByPaymentId(paymentId);
    }

    @Override
    public boolean existClientByPaymentId(@PathVariable Long paymentId) {
        return userService.existsClientByPaymentId(paymentId);
    }
}
