package com.booking.event.service.organization;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

interface OrganizationSearchSpecification {
    static Specification<Organization> organizationFilter(OrganizationFindDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "id", dto.getId()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "name", dto.getName()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "location", dto.getLocation()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "phone", dto.getPhone()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "email", dto.getEmail()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "visible", dto.getVisible()));
            predicates.add(toEqualsPredicate(root, "abstractEvents", dto.getAbstractEvents()));
            Object[] rawPredicates = predicates.stream().filter(Objects::nonNull).toArray();
            return criteriaBuilder.and(Arrays.copyOf(rawPredicates, rawPredicates.length, Predicate[].class));
        };
    }

    static Predicate toEqualsPredicate(Root<Organization> root, CriteriaBuilder criteriaBuilder, String param, Object paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param), paramValue) : null;
    }

    static Predicate toLikePredicate(Root<Organization> root, CriteriaBuilder criteriaBuilder, String param, Object paramValue) {
        return paramValue != null ? criteriaBuilder.like(root.get(param), "%" + paramValue + "%") : null;
    }

    static Predicate toEqualsPredicate(Root<Organization> root, String param, Set<Long> paramValue) {
        return paramValue != null ? root.join(param).in(paramValue) : null;
    }
}
