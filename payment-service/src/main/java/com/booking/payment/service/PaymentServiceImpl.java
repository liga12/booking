package com.booking.payment.service;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import com.booking.payment.transpor.mapper.PaymentMapper;
import com.booking.payment.exception.ExistTokenException;
import com.booking.payment.persistence.repository.PaymentRepository;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Override
    public Page<PaymentOutcomeDto> getAll(PaymentFindDto dto, Pageable pageable) {
        Page<Payment> result = paymentRepository.findAll(
                PaymentSearchSpecification.paymentFilter(dto),
                pageable
        );
        return result.map(paymentMapper::toDto);
    }

    @Override
    public Long create(PaymentCreateDto dto) {
        validateToken(dto.getToken());
        return paymentRepository.save(
                paymentMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public boolean existTokenId(Long id) {
        return paymentRepository.existsById(id);
    }

    @Override
    public Set<Long> entityToId(Set<OrderClient> clients) {
        Set<Long> ids = new HashSet<>();
        for (OrderClient client : clients) {
            ids.add(client.getId());
        }
        return ids;
    }

    private void validateToken(String token) {
        if (paymentRepository.existsByToken(token)) {
            throw new ExistTokenException();
        }
    }

}
