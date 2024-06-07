package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.MenuType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuTypeDTO)) {
            return false;
        }

        MenuTypeDTO menuTypeDTO = (MenuTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
