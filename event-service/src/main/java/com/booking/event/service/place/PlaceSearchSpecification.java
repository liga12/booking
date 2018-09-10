package com.booking.event.service.place;

import com.booking.event.persistence.entity.place.Place;
import com.booking.event.transport.dto.place.PlaceFindDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

interface PlaceSearchSpecification {
    static Specification<Place> placeFilter(PlaceFindDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "id", dto.getId()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "price", dto.getPrice()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "number", dto.getNumber()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "row", dto.getRow()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "status", dto.getStatus()));
            predicates.add(toEqualsPredicateId(root, criteriaBuilder,"events", dto.getEvent()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "sectionType", dto.getSectionType()));
            Object[] rawPredicates = predicates.stream().filter(Objects::nonNull).toArray();
            return criteriaBuilder.and(Arrays.copyOf(rawPredicates, rawPredicates.length, Predicate[].class));
        };
    }

    static Predicate toEqualsPredicate(Root<Place> root,
                                       CriteriaBuilder criteriaBuilder,
                                       String param,
                                       Object paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param), paramValue) : null;
    }

    static Predicate toEqualsPredicateId(Root<Place> root,
                                       CriteriaBuilder criteriaBuilder,
                                       String param,
                                       Long paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param).get("id"), paramValue) : null;
    }
}
