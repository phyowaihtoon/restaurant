package creatip.restaurant.service.impl;

import creatip.restaurant.domain.MenuGroup;
import creatip.restaurant.repository.MenuGroupRepository;
import creatip.restaurant.service.MenuGroupService;
import creatip.restaurant.service.dto.MenuGroupDTO;
import creatip.restaurant.service.mapper.MenuGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link creatip.restaurant.domain.MenuGroup}.
 */
@Service
@Transactional
public class MenuGroupServiceImpl implements MenuGroupService {

    private final Logger log = LoggerFactory.getLogger(MenuGroupServiceImpl.class);

    private final MenuGroupRepository menuGroupRepository;

    private final MenuGroupMapper menuGroupMapper;

    public MenuGroupServiceImpl(MenuGroupRepository menuGroupRepository, MenuGroupMapper menuGroupMapper) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuGroupMapper = menuGroupMapper;
    }

    @Override
    public MenuGroupDTO save(MenuGroupDTO menuGroupDTO) {
        log.debug("Request to save MenuGroup : {}", menuGroupDTO);
        MenuGroup menuGroup = menuGroupMapper.toEntity(menuGroupDTO);
        menuGroup = menuGroupRepository.save(menuGroup);
        return menuGroupMapper.toDto(menuGroup);
    }

    @Override
    public MenuGroupDTO update(MenuGroupDTO menuGroupDTO) {
        log.debug("Request to update MenuGroup : {}", menuGroupDTO);
        MenuGroup menuGroup = menuGroupMapper.toEntity(menuGroupDTO);
        menuGroup = menuGroupRepository.save(menuGroup);
        return menuGroupMapper.toDto(menuGroup);
    }

    @Override
    public Optional<MenuGroupDTO> partialUpdate(MenuGroupDTO menuGroupDTO) {
        log.debug("Request to partially update MenuGroup : {}", menuGroupDTO);

        return menuGroupRepository
            .findById(menuGroupDTO.getId())
            .map(existingMenuGroup -> {
                menuGroupMapper.partialUpdate(existingMenuGroup, menuGroupDTO);

                return existingMenuGroup;
            })
            .map(menuGroupRepository::save)
            .map(menuGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuGroupDTO> findAll() {
        log.debug("Request to get all MenuGroups");
        return menuGroupRepository.findAll().stream().map(menuGroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuGroupDTO> findOne(Long id) {
        log.debug("Request to get MenuGroup : {}", id);
        return menuGroupRepository.findById(id).map(menuGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MenuGroup : {}", id);
        menuGroupRepository.deleteById(id);
    }
}
