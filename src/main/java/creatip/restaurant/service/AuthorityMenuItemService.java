package creatip.restaurant.service;

import creatip.restaurant.service.dto.AuthorityMenuItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link creatip.restaurant.domain.AuthorityMenuItem}.
 */
public interface AuthorityMenuItemService {
    /**
     * Save a authorityMenuItem.
     *
     * @param authorityMenuItemDTO the entity to save.
     * @return the persisted entity.
     */
    AuthorityMenuItemDTO save(AuthorityMenuItemDTO authorityMenuItemDTO);

    /**
     * Updates a authorityMenuItem.
     *
     * @param authorityMenuItemDTO the entity to update.
     * @return the persisted entity.
     */
    AuthorityMenuItemDTO update(AuthorityMenuItemDTO authorityMenuItemDTO);

    /**
     * Partially updates a authorityMenuItem.
     *
     * @param authorityMenuItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AuthorityMenuItemDTO> partialUpdate(AuthorityMenuItemDTO authorityMenuItemDTO);

    /**
     * Get all the authorityMenuItems.
     *
     * @return the list of entities.
     */
    List<AuthorityMenuItemDTO> findAll();

    /**
     * Get the "id" authorityMenuItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthorityMenuItemDTO> findOne(Long id);

    /**
     * Delete the "id" authorityMenuItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
