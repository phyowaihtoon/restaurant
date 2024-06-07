package creatip.restaurant.repository;

import creatip.restaurant.domain.ProductDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {}
