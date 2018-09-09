package com.booking.event.service.event;

import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.transport.dto.event.AbstractEventFindDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

interface EventSearchSpecification {
    static Specification<AbstractEvent> eventFilter(AbstractEventFindDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "id", dto.getId()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "name", dto.getName()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "type", dto.getType()));
            //TODO create for date
            predicates.add(toLikePredicate(root, criteriaBuilder, "description", dto.getDescription()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "location", dto.getLocation()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "photoUrl", dto.getPhotoUrl()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "organization", dto.getOrganization()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "artists", dto.getArtists()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "visible", dto.getVisible()));
            predicates.add(toEqualsPredicate(root, "places", dto.getPlaces()));
            Object[] rawPredicates = predicates.stream().filter(Objects::nonNull).toArray();
            return criteriaBuilder.and(Arrays.copyOf(rawPredicates, rawPredicates.length, Predicate[].class));
        };
    }

    static Predicate toEqualsPredicate(Root<AbstractEvent> root,
                                       CriteriaBuilder criteriaBuilder,
                                       String param,
                                       Object paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param), paramValue) : null;
    }

    static Predicate toLikePredicate(Root<AbstractEvent> root,
                                     CriteriaBuilder criteriaBuilder,
                                     String param,
                                     Object paramValue) {
        return paramValue != null ? criteriaBuilder.like(root.get(param), "%" + paramValue + "%") : null;
    }

    static Predicate toEqualsPredicate(Root<AbstractEvent> root,
                                       CriteriaBuilder criteriaBuilder,
                                       String param,
                                       Long paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param).get("id"), paramValue) : null;
    }

    static Predicate toEqualsPredicate(Root<AbstractEvent> root, String param, Set<Long> paramValue) {
        return paramValue != null ? root.join(param).get("id").in(paramValue) : null;
    }
}
