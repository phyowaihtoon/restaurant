package creatip.restaurant.service.impl;

import creatip.restaurant.domain.AuthorityMenuItem;
import creatip.restaurant.repository.AuthorityMenuItemRepository;
import creatip.restaurant.service.AuthorityMenuItemService;
import creatip.restaurant.service.dto.AuthorityMenuItemDTO;
import creatip.restaurant.service.mapper.AuthorityMenuItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link creatip.restaurant.domain.AuthorityMenuItem}.
 */
@Service
@Transactional
public class AuthorityMenuItemServiceImpl implements AuthorityMenuItemService {

    private final Logger log = LoggerFactory.getLogger(AuthorityMenuItemServiceImpl.class);

    private final AuthorityMenuItemRepository authorityMenuItemRepository;

    private final AuthorityMenuItemMapper authorityMenuItemMapper;

    public AuthorityMenuItemServiceImpl(
        AuthorityMenuItemRepository authorityMenuItemRepository,
        AuthorityMenuItemMapper authorityMenuItemMapper
    ) {
        this.authorityMenuItemRepository = authorityMenuItemRepository;
        this.authorityMenuItemMapper = authorityMenuItemMapper;
    }

    @Override
    public AuthorityMenuItemDTO save(AuthorityMenuItemDTO authorityMenuItemDTO) {
        log.debug("Request to save AuthorityMenuItem : {}", authorityMenuItemDTO);
        AuthorityMenuItem authorityMenuItem = authorityMenuItemMapper.toEntity(authorityMenuItemDTO);
        authorityMenuItem = authorityMenuItemRepository.save(authorityMenuItem);
        return authorityMenuItemMapper.toDto(authorityMenuItem);
    }

    @Override
    public AuthorityMenuItemDTO update(AuthorityMenuItemDTO authorityMenuItemDTO) {
        log.debug("Request to update AuthorityMenuItem : {}", authorityMenuItemDTO);
        AuthorityMenuItem authorityMenuItem = authorityMenuItemMapper.toEntity(authorityMenuItemDTO);
        authorityMenuItem = authorityMenuItemRepository.save(authorityMenuItem);
        return authorityMenuItemMapper.toDto(authorityMenuItem);
    }

    @Override
    public Optional<AuthorityMenuItemDTO> partialUpdate(AuthorityMenuItemDTO authorityMenuItemDTO) {
        log.debug("Request to partially update AuthorityMenuItem : {}", authorityMenuItemDTO);

        return authorityMenuItemRepository
            .findById(authorityMenuItemDTO.getId())
            .map(existingAuthorityMenuItem -> {
                authorityMenuItemMapper.partialUpdate(existingAuthorityMenuItem, authorityMenuItemDTO);

                return existingAuthorityMenuItem;
            })
            .map(authorityMenuItemRepository::save)
            .map(authorityMenuItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorityMenuItemDTO> findAll() {
        log.debug("Request to get all AuthorityMenuItems");
        return authorityMenuItemRepository
            .findAll()
            .stream()
            .map(authorityMenuItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorityMenuItemDTO> findOne(Long id) {
        log.debug("Request to get AuthorityMenuItem : {}", id);
        return authorityMenuItemRepository.findById(id).map(authorityMenuItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuthorityMenuItem : {}", id);
        authorityMenuItemRepository.deleteById(id);
    }
}
