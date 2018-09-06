package com.booking.event.service;

import com.booking.event.exception.OrganizationNameExistException;
import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.transport.dto.OrganizationCreateDto;
import com.booking.event.transport.dto.OrganizationOutcomeDto;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Getter
    private OrganizationMapper organizationMapper;

    @Autowired
    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    public Long create(OrganizationCreateDto dto) {
        validateNameAndEmail(dto.getName());
        return organizationRepository.save(
                organizationMapper.toEntity(dto)
        ).getId();
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

    private void validateNameAndEmail(String name) {
        boolean existName = organizationRepository.existsByName(name);
        if (existName) {
            throw new OrganizationNameExistException();

        }
    }
}
