package com.booking.user.service.feign;

import com.booking.payment.api.PaymentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("payment-service")
public interface PaymentService extends PaymentApi {
}
