package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.Authority;
import creatip.restaurant.domain.AuthorityMenuItem;
import creatip.restaurant.domain.MenuItem;
import creatip.restaurant.service.dto.AuthorityDTO;
import creatip.restaurant.service.dto.AuthorityMenuItemDTO;
import creatip.restaurant.service.dto.MenuItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuthorityMenuItem} and its DTO {@link AuthorityMenuItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorityMenuItemMapper extends EntityMapper<AuthorityMenuItemDTO, AuthorityMenuItem> {
    @Mapping(target = "menuItem", source = "menuItem", qualifiedByName = "menuItemId")
    @Mapping(target = "authority", source = "authority", qualifiedByName = "authorityName")
    AuthorityMenuItemDTO toDto(AuthorityMenuItem s);

    @Named("menuItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuItemDTO toDtoMenuItemId(MenuItem menuItem);

    @Named("authorityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    AuthorityDTO toDtoAuthorityName(Authority authority);
}
