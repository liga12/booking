package com.booking.event.service;

import com.booking.event.exception.ArtistExistException;
import com.booking.event.exception.ArtistNotFoundException;
import com.booking.event.persistence.entity.Artist;
import com.booking.event.persistence.repository.ArtistRepository;
import com.booking.event.transport.dto.ArtistCreateDto;
import com.booking.event.transport.dto.ArtistOutcomeDto;
import com.booking.event.transport.mapper.ArtistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    @Override
    public ArtistOutcomeDto getById(Long id) {
        return artistMapper.toDto(
                artistRepository
                        .findById(id)
                        .orElseThrow(ArtistNotFoundException::new)
        );
    }

    @Override
    public Long create(ArtistCreateDto dto) {
        validateArtist(dto);
        return artistRepository.save(
                artistMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public Set<Artist> getById(Set<Long> ids) {
        Set<Artist> artist = new HashSet<>();
        for (Long id : ids) {
            artist.add(
                    artistMapper.toEntity(getById(id)));
        }
        return artist;
    }

    @Override
    public Set<Long> getIdFromEntity(Set<Artist> artists) {
        Set<Long> ids = new HashSet<>();
        for (Artist artist : artists) {
            ids.add(artist.getId());
        }
        return ids;
    }

    private void validateArtist(ArtistCreateDto dto) {
        boolean exist = artistRepository.existsByName(dto.getName());
        if (exist) {
            throw new ArtistExistException();
        }
    }
}
