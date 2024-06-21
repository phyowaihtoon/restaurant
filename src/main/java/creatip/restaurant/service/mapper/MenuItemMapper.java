package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.MenuGroup;
import creatip.restaurant.domain.MenuItem;
import creatip.restaurant.service.dto.MenuGroupDTO;
import creatip.restaurant.service.dto.MenuItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MenuItem} and its DTO {@link MenuItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuItemMapper extends EntityMapper<MenuItemDTO, MenuItem> {
    @Mapping(target = "menuGroup", source = "menuGroup", qualifiedByName = "menuGroupId")
    MenuItemDTO toDto(MenuItem s);

    @Named("menuGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuGroupDTO toDtoMenuGroupId(MenuGroup menuGroup);
}
