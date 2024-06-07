package creatip.restaurant.service.mapper;

import creatip.restaurant.domain.DiningTable;
import creatip.restaurant.domain.Reservation;
import creatip.restaurant.service.dto.DiningTableDTO;
import creatip.restaurant.service.dto.ReservationDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "diningTables", source = "diningTables", qualifiedByName = "diningTableIdSet")
    ReservationDTO toDto(Reservation s);

    @Mapping(target = "removeDiningTables", ignore = true)
    Reservation toEntity(ReservationDTO reservationDTO);

    @Named("diningTableId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiningTableDTO toDtoDiningTableId(DiningTable diningTable);

    @Named("diningTableIdSet")
    default Set<DiningTableDTO> toDtoDiningTableIdSet(Set<DiningTable> diningTable) {
        return diningTable.stream().map(this::toDtoDiningTableId).collect(Collectors.toSet());
    }
}
