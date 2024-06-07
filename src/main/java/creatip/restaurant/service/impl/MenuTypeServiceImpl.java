package creatip.restaurant.service.impl;

import creatip.restaurant.domain.MenuType;
import creatip.restaurant.repository.MenuTypeRepository;
import creatip.restaurant.service.MenuTypeService;
import creatip.restaurant.service.dto.MenuTypeDTO;
import creatip.restaurant.service.mapper.MenuTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link creatip.restaurant.domain.MenuType}.
 */
@Service
@Transactional
public class MenuTypeServiceImpl implements MenuTypeService {

    private final Logger log = LoggerFactory.getLogger(MenuTypeServiceImpl.class);

    private final MenuTypeRepository menuTypeRepository;

    private final MenuTypeMapper menuTypeMapper;

    public MenuTypeServiceImpl(MenuTypeRepository menuTypeRepository, MenuTypeMapper menuTypeMapper) {
        this.menuTypeRepository = menuTypeRepository;
        this.menuTypeMapper = menuTypeMapper;
    }

    @Override
    public MenuTypeDTO save(MenuTypeDTO menuTypeDTO) {
        log.debug("Request to save MenuType : {}", menuTypeDTO);
        MenuType menuType = menuTypeMapper.toEntity(menuTypeDTO);
        menuType = menuTypeRepository.save(menuType);
        return menuTypeMapper.toDto(menuType);
    }

    @Override
    public MenuTypeDTO update(MenuTypeDTO menuTypeDTO) {
        log.debug("Request to update MenuType : {}", menuTypeDTO);
        MenuType menuType = menuTypeMapper.toEntity(menuTypeDTO);
        menuType = menuTypeRepository.save(menuType);
        return menuTypeMapper.toDto(menuType);
    }

    @Override
    public Optional<MenuTypeDTO> partialUpdate(MenuTypeDTO menuTypeDTO) {
        log.debug("Request to partially update MenuType : {}", menuTypeDTO);

        return menuTypeRepository
            .findById(menuTypeDTO.getId())
            .map(existingMenuType -> {
                menuTypeMapper.partialUpdate(existingMenuType, menuTypeDTO);

                return existingMenuType;
            })
            .map(menuTypeRepository::save)
            .map(menuTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MenuTypes");
        return menuTypeRepository.findAll(pageable).map(menuTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuTypeDTO> findOne(Long id) {
        log.debug("Request to get MenuType : {}", id);
        return menuTypeRepository.findById(id).map(menuTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MenuType : {}", id);
        menuTypeRepository.deleteById(id);
    }
}
