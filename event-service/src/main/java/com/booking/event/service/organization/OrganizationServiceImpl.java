package com.booking.event.service.organization;

import com.booking.event.exception.OrganizationNameExistException;
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
import com.booking.event.transport.mapper.AbstractEventMapper;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AbstractEventService abstractEventService;

    @Autowired
    private AbstractEventMapper eventMapper;

    @Autowired
    private AbstractEventRepository abstractEventRepository;

    @Getter
    private OrganizationMapper organizationMapper;

    @Autowired
    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationOutcomeDto> getAll(OrganizationFindDto dto, Pageable pageable) {
        Page<Organization> result = organizationRepository.findAll(
                OrganizationSearchSpecification.organizationFilter(dto),
                pageable
        );
        return result.map(organizationMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public OrganizationOutcomeDto getById(Long id) {
        return organizationMapper.toDto(
                organizationRepository
                        .findById(id)
                        .orElseThrow(OrganizationNotFoundException::new)
        );
    }

    @Override
    @Transactional
    public Long create(OrganizationCreateDto dto) {
        validateExistsName(dto.getName());
        return organizationRepository.save(
                organizationMapper.toEntity(dto)
        ).getId();
    }

    @Override
    @Transactional
    public Long update(OrganizationUpdateDto dto) {
        validateExistingById(dto.getId());
        validateExistNameAndId(dto.getName(), dto.getId());
        abstractEventService.getById(dto.getEvents());
        return organizationRepository.save(
                organizationMapper.toEntity(dto)
        ).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        OrganizationOutcomeDto dto = getById(id);
        Set<AbstractEvent> events = abstractEventService.getById(dto.getEvents());
        for (AbstractEvent event : events) {
            if (event.getVisible()) {
                event.setVisible(false);
                abstractEventRepository.save(event);
            }
        }
        Organization organization = organizationMapper.toEntity(getById(id));
        organization.setVisible(false);
        organizationRepository.save(organization);
    }

    private void validateExistNameAndId(String name, Long id) {
        if (id == null || organizationRepository.existsByNameAndIdIsNot(name, id)) {
            throw new OrganizationNameExistException();
        }
    }


    private void validateExistingById(Long id) {
        if (id == null || !organizationRepository.existsById(id)) {
            throw new OrganizationNotFoundException();
        }
    }

    private void validateExistsName(String name) {
        boolean existName = organizationRepository.existsByName(name);
        if (existName) {
            throw new OrganizationNameExistException();

        }
    }
}
