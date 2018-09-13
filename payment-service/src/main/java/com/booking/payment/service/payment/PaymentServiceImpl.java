package com.booking.payment.service.payment;

import com.booking.payment.exception.ExistTokenException;
import com.booking.payment.exception.PaymentNotFoundException;
import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.persistence.repository.PaymentRepository;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import com.booking.payment.transpor.mapper.PaymentMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private PaymentMapper paymentMapper;

    @Autowired
    public void setPaymentMapper(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Page<PaymentOutcomeDto> getAll(PaymentFindDto dto, Pageable pageable) {
        Page<Payment> result = paymentRepository.findAll(
                PaymentSearchSpecification.paymentFilter(dto),
                pageable
        );
        return result.map(paymentMapper::toDto);
    }

    @Override
    public Payment getById(Long id) {
       return paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
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
    public void validateById(Long id){
        if (!paymentRepository.existsById(id)){
            throw  new PaymentNotFoundException();
        }
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
