package creatip.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MenuItem.
 */
@Entity
@Table(name = "menu_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuItem implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuGroup menuGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MenuItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public MenuItem code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public MenuItem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslateKey() {
        return this.translateKey;
    }

    public MenuItem translateKey(String translateKey) {
        this.setTranslateKey(translateKey);
        return this;
    }

    public void setTranslateKey(String translateKey) {
        this.translateKey = translateKey;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public MenuItem orderNo(Integer orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getFaIcon() {
        return this.faIcon;
    }

    public MenuItem faIcon(String faIcon) {
        this.setFaIcon(faIcon);
        return this;
    }

    public void setFaIcon(String faIcon) {
        this.faIcon = faIcon;
    }

    public String getRouterLink() {
        return this.routerLink;
    }

    public MenuItem routerLink(String routerLink) {
        this.setRouterLink(routerLink);
        return this;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public MenuGroup getMenuGroup() {
        return this.menuGroup;
    }

    public void setMenuGroup(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public MenuItem menuGroup(MenuGroup menuGroup) {
        this.setMenuGroup(menuGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItem)) {
            return false;
        }
        return getId() != null && getId().equals(((MenuItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuItem{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", translateKey='" + getTranslateKey() + "'" +
            ", orderNo=" + getOrderNo() +
            ", faIcon='" + getFaIcon() + "'" +
            ", routerLink='" + getRouterLink() + "'" +
            "}";
    }
}
