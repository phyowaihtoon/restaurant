package creatip.restaurant.repository;

import creatip.restaurant.domain.MenuGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {}
