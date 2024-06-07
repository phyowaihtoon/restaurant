package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.DiningTable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiningTableDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer tableNumber;

    @NotNull
    private Integer seatingCapacity;

    @NotNull
    private Integer tableStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Integer getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(Integer tableStatus) {
        this.tableStatus = tableStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiningTableDTO)) {
            return false;
        }

        DiningTableDTO diningTableDTO = (DiningTableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, diningTableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiningTableDTO{" +
            "id=" + getId() +
            ", tableNumber=" + getTableNumber() +
            ", seatingCapacity=" + getSeatingCapacity() +
            ", tableStatus=" + getTableStatus() +
            "}";
    }
}
