package creatip.restaurant.repository;

import creatip.restaurant.domain.DiningTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DiningTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {}
