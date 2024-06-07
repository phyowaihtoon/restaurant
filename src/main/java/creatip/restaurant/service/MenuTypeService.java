package creatip.restaurant.service;

import creatip.restaurant.service.dto.MenuTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link creatip.restaurant.domain.MenuType}.
 */
public interface MenuTypeService {
    /**
     * Save a menuType.
     *
     * @param menuTypeDTO the entity to save.
     * @return the persisted entity.
     */
    MenuTypeDTO save(MenuTypeDTO menuTypeDTO);

    /**
     * Updates a menuType.
     *
     * @param menuTypeDTO the entity to update.
     * @return the persisted entity.
     */
    MenuTypeDTO update(MenuTypeDTO menuTypeDTO);

    /**
     * Partially updates a menuType.
     *
     * @param menuTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MenuTypeDTO> partialUpdate(MenuTypeDTO menuTypeDTO);

    /**
     * Get all the menuTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MenuTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" menuType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuTypeDTO> findOne(Long id);

    /**
     * Delete the "id" menuType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
