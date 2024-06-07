package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.MenuType;
import creatip.restaurant.service.dto.MenuTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MenuType} and its DTO {@link MenuTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuTypeMapper extends EntityMapper<MenuTypeDTO, MenuType> {}
