package creatip.restaurant.service;

import creatip.restaurant.service.dto.DiningTableDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link creatip.restaurant.domain.DiningTable}.
 */
public interface DiningTableService {
    /**
     * Save a diningTable.
     *
     * @param diningTableDTO the entity to save.
     * @return the persisted entity.
     */
    DiningTableDTO save(DiningTableDTO diningTableDTO);

    /**
     * Updates a diningTable.
     *
     * @param diningTableDTO the entity to update.
     * @return the persisted entity.
     */
    DiningTableDTO update(DiningTableDTO diningTableDTO);

    /**
     * Partially updates a diningTable.
     *
     * @param diningTableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiningTableDTO> partialUpdate(DiningTableDTO diningTableDTO);

    /**
     * Get all the diningTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiningTableDTO> findAll(Pageable pageable);

    /**
     * Get the "id" diningTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiningTableDTO> findOne(Long id);

    /**
     * Delete the "id" diningTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
