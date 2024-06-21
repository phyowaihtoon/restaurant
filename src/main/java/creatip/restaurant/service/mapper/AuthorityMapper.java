package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.Authority;
import creatip.restaurant.service.dto.AuthorityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Authority} and its DTO {@link AuthorityDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {}
