package creatip.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MenuGroup.
 */
@Entity
@Table(name = "menu_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "is_child")
    private Integer isChild;

    @Column(name = "parent_id")
    private Long parentId;

    @Size(max = 100)
    @Column(name = "translate_key", length = 100)
    private String translateKey;

    @Column(name = "order_no")
    private Integer orderNo;

    @Size(max = 50)
    @Column(name = "fa_icon", length = 50)
    private String faIcon;

    @Size(max = 50)
    @Column(name = "router_link", length = 50)
    private String routerLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MenuGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public MenuGroup code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public MenuGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsChild() {
        return this.isChild;
    }

    public MenuGroup isChild(Integer isChild) {
        this.setIsChild(isChild);
        return this;
    }

    public void setIsChild(Integer isChild) {
        this.isChild = isChild;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public MenuGroup parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTranslateKey() {
        return this.translateKey;
    }

    public MenuGroup translateKey(String translateKey) {
        this.setTranslateKey(translateKey);
        return this;
    }

    public void setTranslateKey(String translateKey) {
        this.translateKey = translateKey;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public MenuGroup orderNo(Integer orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getFaIcon() {
        return this.faIcon;
    }

    public MenuGroup faIcon(String faIcon) {
        this.setFaIcon(faIcon);
        return this;
    }

    public void setFaIcon(String faIcon) {
        this.faIcon = faIcon;
    }

    public String getRouterLink() {
        return this.routerLink;
    }

    public MenuGroup routerLink(String routerLink) {
        this.setRouterLink(routerLink);
        return this;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((MenuGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuGroup{" +
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
