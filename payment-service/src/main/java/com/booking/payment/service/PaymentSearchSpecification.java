package com.booking.payment.service;

import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.transpor.dto.PaymentFindDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

interface PaymentSearchSpecification {
    static Specification<Payment> paymentFilter(PaymentFindDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "id", dto.getId()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "token", dto.getToken()));
            predicates.add(toEqualsPredicate(root, "client", dto.getClient()));
            predicates.add(toEqualsPredicate(root, "customer", dto.getCustomer()));
            Object[] rawPredicates = predicates.stream().filter(Objects::nonNull).toArray();
            return criteriaBuilder.and(Arrays.copyOf(rawPredicates, rawPredicates.length, Predicate[].class));
        };
    }

    static Predicate toEqualsPredicate(Root<Payment> root, CriteriaBuilder criteriaBuilder, String param, Object paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param), paramValue) : null;
    }

    static Predicate toEqualsPredicate(Root<Payment> root, String param, Set<Long> paramValue) {
        return paramValue != null ? root.join(param).get("id").in(paramValue) : null;
    }
}
