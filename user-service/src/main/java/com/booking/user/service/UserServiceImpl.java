package com.booking.user.service;

import com.booking.user.exception.EmailExistException;
import com.booking.user.exception.TokenNotExistException;
import com.booking.user.exception.UserNotFoundException;
import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.service.feign.PaymentService;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import com.booking.user.transpor.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PaymentService paymentService;

    private final MongoTemplate mongoTemplate;

    @Override
    public List<UserOutcomeDto> getAll(UserFindDto dto, Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(toEquals("id", dto.getId()));
        criteriaList.add(toEquals("paymentId", dto.getPaymentId()));
        criteriaList.add(toLike("name", dto.getName()));
        criteriaList.add(toLike("surname", dto.getSurname()));
        criteriaList.add(toEquals("type", dto.getType()));
        criteriaList.add(toLike("email", dto.getEmail()));
        criteriaList.add(toLike("phone", dto.getPhone()));
        criteriaList.forEach(q -> {
            if (q != null) {
                query.addCriteria(q);
            }
        });
        List<User> students = mongoTemplate.find(query, User.class);
        return userMapper.toDto(students);
    }

    private Criteria toEquals(String param, Object paramValue) {
        return param != null && paramValue != null ? Criteria.where(param).is(paramValue) : null;
    }

    private Criteria toLike(String param, String paramValue) {
        return param != null && paramValue != null ? Criteria.where(param).regex(paramValue) : null;
    }

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

    @Override
    public boolean existsCustomerByPaymentId(Long paymentId) {
        return userRepository.existsByPaymentIdAndType(paymentId, UserType.CUSTOMER);
    }

    @Override
    public boolean existsClientByPaymentId(Long paymentId) {
        return userRepository.existsByPaymentIdAndType(paymentId, UserType.CLIENT);
    }

    @Override
    public UserOutcomeDto getUserByPaymentId(Long paymentClientId) {
        UserFindDto dto = new UserFindDto();
        dto.setPaymentId(paymentClientId);
        List<UserOutcomeDto> users = getAll(dto, PageRequest.of(0, 1));
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }
        return users.get(0);
    }

    private void validateEmail(String email) {
        if (email==null||userRepository.existsByEmail(email)) {
            throw new EmailExistException();
        }
    }

    private void validateTokenId(Long tokenId) {
        if (tokenId==null||!paymentService.existTokenId(tokenId)) {
            throw new TokenNotExistException();
        }
    }
}
