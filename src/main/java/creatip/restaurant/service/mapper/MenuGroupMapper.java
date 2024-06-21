package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.MenuGroup;
import creatip.restaurant.service.dto.MenuGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MenuGroup} and its DTO {@link MenuGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuGroupMapper extends EntityMapper<MenuGroupDTO, MenuGroup> {}
