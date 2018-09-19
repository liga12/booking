package com.booking.user.asserts;

import com.booking.user.transpor.dto.UserOutcomeDto;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public interface Asserts {

    static void assertEqualUserAndDto(List<UserOutcomeDto> firstDto, List<UserOutcomeDto> secondDto) {
        if (firstDto.size() != secondDto.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstDto.size(); i++) {
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getPaymentId(), secondDto.get(i).getPaymentId());
            assertEquals(firstDto.get(i).getName(), secondDto.get(i).getName());
            assertEquals(firstDto.get(i).getSurname(), secondDto.get(i).getSurname());
            assertEquals(firstDto.get(i).getType(), secondDto.get(i).getType());
            assertEquals(firstDto.get(i).getEmail(), secondDto.get(i).getEmail());
            assertEquals(firstDto.get(i).getPhone(), secondDto.get(i).getPhone());
        }
    }

    static void assertEqualUserAndDto(UserOutcomeDto firstDto, UserOutcomeDto secondDto) {
        assertEquals(firstDto.getId(), secondDto.getId());
        assertEquals(firstDto.getPaymentId(), secondDto.getPaymentId());
        assertEquals(firstDto.getName(), secondDto.getName());
        assertEquals(firstDto.getSurname(), secondDto.getSurname());
        assertEquals(firstDto.getType(), secondDto.getType());
        assertEquals(firstDto.getEmail(), secondDto.getEmail());
        assertEquals(firstDto.getPhone(), secondDto.getPhone());
    }

    static void assertEqualArrays(InputStream first, InputStream second) throws IOException {
        byte[] firstBytes = IOUtils.toByteArray(first);
        byte[] secondBytes = IOUtils.toByteArray(second);
        if (firstBytes.length != secondBytes.length) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstBytes.length; i++) {
            assertEquals(firstBytes[i], secondBytes[i]);
        }
    }
}

