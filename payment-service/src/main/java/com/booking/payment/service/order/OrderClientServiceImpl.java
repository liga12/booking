package com.booking.payment.service.order;

import com.booking.payment.exception.ClientNotFoundException;
import com.booking.payment.exception.CustomerNotFoundException;
import com.booking.payment.exception.PlaceNotFoundException;
import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.service.feign.EventService;
import com.booking.payment.service.feign.UserService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import com.booking.payment.transpor.mapper.OrderClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderClientRepository orderClientRepository;

    private final OrderClientMapper orderClientMapper;

    private final EventService eventService;

    private final UserService userService;

    @Override
    public Page<OrderClientOutcomeDto> getAll(OrderClientFindDto dto, Pageable pageable) {
        return orderClientRepository.findAll(
                OrderClientSearchSpecification.orderFilter(dto),
                pageable).map(orderClientMapper::toDto);
    }

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
        if (placeId == null || !eventService.existsActivePlace(placeId)) {
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
