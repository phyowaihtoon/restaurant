package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.MenuGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 255)
    private String name;

    @Min(value = 0)
    @Max(value = 1)
    private Integer isChild;

    private Long parentId;

    @Size(max = 100)
    private String translateKey;

    private Integer orderNo;

    @Size(max = 50)
    private String faIcon;

    @Size(max = 50)
    private String routerLink;

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

    public Integer getIsChild() {
        return isChild;
    }

    public void setIsChild(Integer isChild) {
        this.isChild = isChild;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroupDTO)) {
            return false;
        }

        MenuGroupDTO menuGroupDTO = (MenuGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuGroupDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isChild=" + getIsChild() +
            ", parentId=" + getParentId() +
            ", translateKey='" + getTranslateKey() + "'" +
            ", orderNo=" + getOrderNo() +
            ", faIcon='" + getFaIcon() + "'" +
            ", routerLink='" + getRouterLink() + "'" +
            "}";
    }
}
