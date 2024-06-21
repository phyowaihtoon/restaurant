package creatip.restaurant.service;

import creatip.restaurant.service.dto.MenuGroupDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link creatip.restaurant.domain.MenuGroup}.
 */
public interface MenuGroupService {
    /**
     * Save a menuGroup.
     *
     * @param menuGroupDTO the entity to save.
     * @return the persisted entity.
     */
    MenuGroupDTO save(MenuGroupDTO menuGroupDTO);

    /**
     * Updates a menuGroup.
     *
     * @param menuGroupDTO the entity to update.
     * @return the persisted entity.
     */
    MenuGroupDTO update(MenuGroupDTO menuGroupDTO);

    /**
     * Partially updates a menuGroup.
     *
     * @param menuGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MenuGroupDTO> partialUpdate(MenuGroupDTO menuGroupDTO);

    /**
     * Get all the menuGroups.
     *
     * @return the list of entities.
     */
    List<MenuGroupDTO> findAll();

    /**
     * Get the "id" menuGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuGroupDTO> findOne(Long id);

    /**
     * Delete the "id" menuGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
