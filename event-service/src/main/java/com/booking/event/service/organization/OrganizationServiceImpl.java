package com.booking.event.service.organization;

import com.booking.event.exception.OrganizationNameExistException;
import com.booking.event.exception.OrganizationNotActiveException;
import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.AbstractEventRepository;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.service.event.AbstractEventService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AbstractEventService abstractEventService;

    @Autowired
    private AbstractEventRepository abstractEventRepository;

    @Getter
    private OrganizationMapper organizationMapper;

    @Autowired
    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    public Page<OrganizationOutcomeDto> getAll(OrganizationFindDto dto, Pageable pageable) {
        Page<Organization> result = organizationRepository.findAll(
                OrganizationSearchSpecification.organizationFilter(dto),
                pageable
        );
        return result.map(organizationMapper::toDto);
    }

    @Override
    public OrganizationOutcomeDto getById(Long id) {
        return organizationMapper.toDto(
                organizationRepository
                        .findById(id)
                        .orElseThrow(OrganizationNotFoundException::new)
        );
    }

    @Override
    public Long create(OrganizationCreateDto dto) {
        validateExistsName(dto.getName());
        return organizationRepository.save(
                organizationMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public Long update(OrganizationUpdateDto dto) {
        validateExistNameAndId(dto.getName(), dto.getId());
        return organizationRepository.save(
                organizationMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public void delete(Long id) {
        Organization organization = organizationMapper.toEntity(getById(id));
        organization.setVisible(false);
        for (AbstractEvent event : organization.getEvents()) {
            abstractEventService.delete(event.getId());
        }
        organizationRepository.save(organization);
    }

    @Override
    public void validateOrganizationByActive(Long id){
        if (!getById(id).getVisible()){
            throw new OrganizationNotActiveException();
        }
    }

    private void validateExistNameAndId(String name, Long id) {
        if (id == null || organizationRepository.existsByNameAndIdIsNot(name, id)) {
            throw new OrganizationNameExistException();
        }
    }

    private void validateExistsName(String name) {
        if (name == null || organizationRepository.existsByName(name)) {
            throw new OrganizationNameExistException();

        }
    }
}
