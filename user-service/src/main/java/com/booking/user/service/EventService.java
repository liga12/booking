package com.booking.user.service;

import com.booking.event.api.EventApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("event-service")
public interface EventService extends EventApi {
}
