package com.booking.user.service;

import com.booking.user.exception.EmailExistException;
import com.booking.user.exception.TokenNotExistException;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PaymentService paymentService;

    @Override
    public String create(UserCreateDto dto) {
        validateEmail(dto.getEmail());
        validateTokenId(dto.getPaymentId());
        return userRepository.save(
                userMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public boolean existsCustomer(String id) {
        return userRepository.existsByIdAndType(id, UserType.CUSTOMER);
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailExistException();
        }
    }

    private void validateTokenId(Long tokenId) {
        if (!paymentService.existTokenId(tokenId)) {
            throw new TokenNotExistException();
        }
    }


}
