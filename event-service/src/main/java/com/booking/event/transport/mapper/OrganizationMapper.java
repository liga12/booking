package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.transport.dto.OrganizationCreateDto;
import com.booking.event.transport.dto.OrganizationOutcomeDto;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring", uses = AbstractEventMapper.class)
@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationOutcomeDto toDto(Organization organization);

    Organization toEntity(OrganizationCreateDto dto);

    Organization toEntity(OrganizationOutcomeDto dto);
}
