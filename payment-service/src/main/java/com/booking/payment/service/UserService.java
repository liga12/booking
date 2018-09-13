package com.booking.payment.service;

import com.booking.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserService extends UserApi {
}
