package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.MenuItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuItemDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 100)
    private String translateKey;

    private Integer orderNo;

    @Size(max = 50)
    private String faIcon;

    @Size(max = 50)
    private String routerLink;

    private MenuGroupDTO menuGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslateKey() {
        return translateKey;
    }

    public void setTranslateKey(String translateKey) {
        this.translateKey = translateKey;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getFaIcon() {
        return faIcon;
    }

    public void setFaIcon(String faIcon) {
        this.faIcon = faIcon;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public MenuGroupDTO getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroupDTO menuGroup) {
        this.menuGroup = menuGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItemDTO)) {
            return false;
        }

        MenuItemDTO menuItemDTO = (MenuItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuItemDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", translateKey='" + getTranslateKey() + "'" +
            ", orderNo=" + getOrderNo() +
            ", faIcon='" + getFaIcon() + "'" +
            ", routerLink='" + getRouterLink() + "'" +
            ", menuGroup=" + getMenuGroup() +
            "}";
    }
}
