package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.AuthorityMenuItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthorityMenuItemDTO implements Serializable {

    private Long id;

    @Min(value = 0)
    @Max(value = 1)
    private Integer isAllow;

    @Min(value = 0)
    @Max(value = 1)
    private Integer isRead;

    @Min(value = 0)
    @Max(value = 1)
    private Integer isWrite;

    @Min(value = 0)
    @Max(value = 1)
    private Integer isDelete;

    private MenuItemDTO menuItem;

    private AuthorityDTO authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(Integer isAllow) {
        this.isAllow = isAllow;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(Integer isWrite) {
        this.isWrite = isWrite;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public MenuItemDTO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem) {
        this.menuItem = menuItem;
    }

    public AuthorityDTO getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityDTO authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorityMenuItemDTO)) {
            return false;
        }

        AuthorityMenuItemDTO authorityMenuItemDTO = (AuthorityMenuItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, authorityMenuItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorityMenuItemDTO{" +
            "id=" + getId() +
            ", isAllow=" + getIsAllow() +
            ", isRead=" + getIsRead() +
            ", isWrite=" + getIsWrite() +
            ", isDelete=" + getIsDelete() +
            ", menuItem=" + getMenuItem() +
            ", authority=" + getAuthority() +
            "}";
    }
}
