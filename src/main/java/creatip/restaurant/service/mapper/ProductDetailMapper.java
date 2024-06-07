package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.Product;
import creatip.restaurant.domain.ProductDetail;
import creatip.restaurant.service.dto.ProductDTO;
import creatip.restaurant.service.dto.ProductDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDetail} and its DTO {@link ProductDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductDetailMapper extends EntityMapper<ProductDetailDTO, ProductDetail> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    ProductDetailDTO toDto(ProductDetail s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
