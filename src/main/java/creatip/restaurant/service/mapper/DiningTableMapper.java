package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.DiningTable;
import creatip.restaurant.service.dto.DiningTableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiningTable} and its DTO {@link DiningTableDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiningTableMapper extends EntityMapper<DiningTableDTO, DiningTable> {}
