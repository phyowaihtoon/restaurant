package creatip.restaurant.repository;

import creatip.restaurant.domain.MenuType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuTypeRepository extends JpaRepository<MenuType, Long> {}
