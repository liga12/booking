package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.service.event.AbstractEventService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@NoArgsConstructor
public abstract class OrganizationMapper {

    @Getter
    AbstractEventService abstractEventService;

    @Autowired
    public void setAbstractEventService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @Mapping(target = "abstractEvents",
            expression = "java(abstractEventService.getIdFromEntity(organization.getAbstractEvents()))")
    public abstract OrganizationOutcomeDto toDto(Organization organization);

    public abstract Organization toEntity(OrganizationCreateDto dto);

    public abstract Organization toEntity(OrganizationUpdateDto dto);

    @Mapping(target = "abstractEvents",
            expression = "java(abstractEventService.getById(dto.getAbstractEvents()))")
    public abstract Organization toEntity(OrganizationOutcomeDto dto);

}
