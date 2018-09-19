package com.booking.payment.asserts;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;

import java.util.List;

import static org.junit.Assert.assertEquals;

public interface Asserts {

    static void assertEqualOrderClientAndDto(List<OrderClient> firstDto, List<OrderClientOutcomeDto> secondDto) {
        if (firstDto.size() != secondDto.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstDto.size(); i++) {
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getPlaceId(), secondDto.get(i).getPlaceId());
            assertEquals(firstDto.get(i).getPaymentClient().getId(), secondDto.get(i).getPaymentClient());
            assertEquals(firstDto.get(i).getPaymentCustomer().getId(), secondDto.get(i).getPaymentCustomer());
            assertEquals(firstDto.get(i).getAmount(), secondDto.get(i).getAmount());
        }
    }

    static void assertEqualPaymentClientAndDto(List<Payment> firstDto, List<PaymentOutcomeDto> secondDto) {
        if (firstDto.size() != secondDto.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstDto.size(); i++) {
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getToken(), secondDto.get(i).getToken());
            assertEquals(firstDto.get(i).getClient().size(), secondDto.get(i).getClient().size());
            assertEquals(firstDto.get(i).getCustomer().size(), secondDto.get(i).getCustomer().size());
        }
    }
}

