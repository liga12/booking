package com.booking.payment.service.order;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

interface OrderClientSearchSpecification {
    static Specification<OrderClient> eventFilter(OrderClientFindDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "id", dto.getId()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "placeId", dto.getPlaceId()));
            predicates.add(toEqualsPredicateId(root, criteriaBuilder, "paymentClient", dto.getPaymentClient()));
            predicates.add(toEqualsPredicateId(root, criteriaBuilder, "paymentCustomer", dto.getPaymentCustomer()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "amount", dto.getAmount()));
            Object[] rawPredicates = predicates.stream().filter(Objects::nonNull).toArray();
            return criteriaBuilder.and(Arrays.copyOf(rawPredicates, rawPredicates.length, Predicate[].class));
        };
    }

    static Predicate toEqualsPredicate(Root<OrderClient> root,
                                       CriteriaBuilder criteriaBuilder,
                                       String param,
                                       Object paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param), paramValue) : null;
    }

    static Predicate toEqualsPredicateId(Root<OrderClient> root,
                                         CriteriaBuilder criteriaBuilder,
                                         String param,
                                         Long paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param).get("id"), paramValue) : null;
    }
}
