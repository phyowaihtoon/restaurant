package creatip.restaurant.repository;

import creatip.restaurant.domain.AuthorityMenuItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuthorityMenuItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityMenuItemRepository extends JpaRepository<AuthorityMenuItem, Long> {}
