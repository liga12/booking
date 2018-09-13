package com.booking.payment.service.order;

import com.booking.payment.exception.ClientNotFoundException;
import com.booking.payment.exception.CustomerNotFoundException;
import com.booking.payment.exception.PlaceNotFoundException;
import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.service.EventService;
import com.booking.payment.service.UserService;
import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.mapper.OrderClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderClientRepository orderClientRepository;

    private final OrderClientMapper orderClientMapper;

    private final EventService eventService;

    private final UserService userService;

    @Override
    public Long create(OrderClientCreateDto dto) {
        validatePlace(dto.getPlaceId());
        validateCustomer(dto.getPaymentCustomer());
        validateClient(dto.getPaymentClient());
        OrderClient orderClient = orderClientMapper.toEntity(dto);
        orderClient.setAmount(eventService.getAmount(dto.getPlaceId()));
        eventService.buyPlace(dto.getPlaceId());
        return orderClientRepository
                .save(orderClient)
                .getId();
    }

    private void validatePlace(Long placeId) {
        if (placeId == null || !eventService.existsPlace(placeId)) {
            throw new PlaceNotFoundException();
        }
    }

    private void validateCustomer(Long id) {
        if (id == null || !userService.existCustomerByPaymentId(id)) {
            throw new CustomerNotFoundException();
        }
    }

    private void validateClient(Long id) {
        if (id == null || !userService.existClientByPaymentId(id)) {
            throw new ClientNotFoundException();
        }
    }
}
