package com.booking.user.service;

import com.booking.event.dto.AbstractEventOutcomeDto;
import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.user.exception.EmailExistException;
import com.booking.user.exception.PlaceNotFoundException;
import com.booking.user.exception.TokenNotExistException;
import com.booking.user.exception.UserNotFoundException;
import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.ticket.TicketService;
import com.booking.user.ticket.Ticket;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import com.booking.user.transpor.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Environment environment;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PaymentService paymentService;

    private final MongoTemplate mongoTemplate;

    private final EventService eventService;

    private final TicketService ticketService;

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
    public String getTicketUrl(Long placeId, Long paymentClientId, Double cost) {
        String controllerMethod = "tickets/getPdf?path=";
        String hostAndPort = "http://localhost:"+environment.getProperty("server.port")+"/";
        Ticket ticket = new Ticket();
        PlaceOutcomeDto placeDto = getPlace(placeId);
        AbstractEventOutcomeDto event = getEvent(placeDto.getEvent());
        String organizationPhone = getOrganizationPhone(event.getOrganization());
        UserOutcomeDto user = getUserByPaymentId(paymentClientId);
        ticket.setName(user.getName());
        ticket.setSurname(user.getSurname());
        ticket.setPlaceNumber(placeDto.getNumber());
        ticket.setPlaceRow(placeDto.getRow());
        ticket.setEvent(event.getName());
        ticket.setDate(longToLocalDateTime(event.getDate()));
        ticket.setCost(cost);
        ticket.setOrganizationPhone(organizationPhone);
        return hostAndPort+controllerMethod + ticketService.createPdf(ticket);
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

    private UserOutcomeDto getUserByPaymentId(Long paymentClientId) {
        UserFindDto dto = UserFindDto.builder().paymentId(paymentClientId).build();
        List<UserOutcomeDto> users = getAll(dto, new PageRequest(0, 1));
        if (getAll(dto, new PageRequest(0, 1)).isEmpty()) {
            throw new UserNotFoundException();
        }
        return users.get(0);

    }

    private PlaceOutcomeDto getPlace(Long placeId) {
        if (placeId == null || !eventService.existsBuyPlace(placeId)) {
            throw new PlaceNotFoundException();
        }
        return eventService.getPlace(placeId);
    }

    private AbstractEventOutcomeDto getEvent(Long eventId) {
        if (eventId == null || !eventService.existsEvent(eventId)) {
            throw new PlaceNotFoundException();
        }
        return eventService.getEvent(eventId);
    }

    private String getOrganizationPhone(Long organizationId) {
        if (organizationId == null || !eventService.existsOrganization(organizationId)) {
            throw new PlaceNotFoundException();
        }
        return eventService.getOrganizationPhone(organizationId);
    }

    private String longToLocalDateTime(Long time) {
        return formatDate(
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(time * 1000),
                        ZoneId.systemDefault()
                )
        );

    }

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }
}
