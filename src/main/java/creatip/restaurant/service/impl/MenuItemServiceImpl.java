package creatip.restaurant.service.impl;

import creatip.restaurant.domain.MenuItem;
import creatip.restaurant.repository.MenuItemRepository;
import creatip.restaurant.service.MenuItemService;
import creatip.restaurant.service.dto.MenuItemDTO;
import creatip.restaurant.service.mapper.MenuItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link creatip.restaurant.domain.MenuItem}.
 */
@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {

    private final Logger log = LoggerFactory.getLogger(MenuItemServiceImpl.class);

    private final MenuItemRepository menuItemRepository;

    private final MenuItemMapper menuItemMapper;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItemDTO save(MenuItemDTO menuItemDTO) {
        log.debug("Request to save MenuItem : {}", menuItemDTO);
        MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toDto(menuItem);
    }

    @Override
    public MenuItemDTO update(MenuItemDTO menuItemDTO) {
        log.debug("Request to update MenuItem : {}", menuItemDTO);
        MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toDto(menuItem);
    }

    @Override
    public Optional<MenuItemDTO> partialUpdate(MenuItemDTO menuItemDTO) {
        log.debug("Request to partially update MenuItem : {}", menuItemDTO);

        return menuItemRepository
            .findById(menuItemDTO.getId())
            .map(existingMenuItem -> {
                menuItemMapper.partialUpdate(existingMenuItem, menuItemDTO);

                return existingMenuItem;
            })
            .map(menuItemRepository::save)
            .map(menuItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemDTO> findAll() {
        log.debug("Request to get all MenuItems");
        return menuItemRepository.findAll().stream().map(menuItemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuItemDTO> findOne(Long id) {
        log.debug("Request to get MenuItem : {}", id);
        return menuItemRepository.findById(id).map(menuItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MenuItem : {}", id);
        menuItemRepository.deleteById(id);
    }
}
