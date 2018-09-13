package com.booking.event.service;

import com.booking.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserService extends UserApi {
}
