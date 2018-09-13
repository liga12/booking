package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.service.event.AbstractEventService;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@NoArgsConstructor
public abstract class OrganizationMapper {

    @Getter
    AbstractEventService abstractEventService;

    @Getter
    OrganizationService organizationService;

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    public void setAbstractEventService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @Mapping(target = "events",
            expression = "java(abstractEventService.getIdFromEntity(organization.getEvents()))")
    public abstract OrganizationOutcomeDto toDto(Organization organization);

    public abstract Organization toEntity(OrganizationCreateDto dto);

    @Mappings({
            @Mapping(target = "events",
                    expression = "java(abstractEventService.getById(organizationService.getById(dto.getId()).getEvents()))"),
            @Mapping(target = "customerId",
                    expression = "java( organizationService.getById(dto.getId()).getCustomerId())")
    })

    public abstract Organization toEntity(OrganizationUpdateDto dto);

    @Mapping(target = "events",
            expression = "java(abstractEventService.getById(dto.getEvents()))")
    public abstract Organization toEntity(OrganizationOutcomeDto dto);

}
