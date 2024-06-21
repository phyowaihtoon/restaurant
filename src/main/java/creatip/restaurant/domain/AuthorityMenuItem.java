package creatip.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuthorityMenuItem.
 */
@Entity
@Table(name = "authority_menu_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthorityMenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "is_allow")
    private Integer isAllow;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "is_read")
    private Integer isRead;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "is_write")
    private Integer isWrite;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "is_delete")
    private Integer isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "menuGroup" }, allowSetters = true)
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Authority authority;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuthorityMenuItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsAllow() {
        return this.isAllow;
    }

    public AuthorityMenuItem isAllow(Integer isAllow) {
        this.setIsAllow(isAllow);
        return this;
    }

    public void setIsAllow(Integer isAllow) {
        this.isAllow = isAllow;
    }

    public Integer getIsRead() {
        return this.isRead;
    }

    public AuthorityMenuItem isRead(Integer isRead) {
        this.setIsRead(isRead);
        return this;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsWrite() {
        return this.isWrite;
    }

    public AuthorityMenuItem isWrite(Integer isWrite) {
        this.setIsWrite(isWrite);
        return this;
    }

    public void setIsWrite(Integer isWrite) {
        this.isWrite = isWrite;
    }

    public Integer getIsDelete() {
        return this.isDelete;
    }

    public AuthorityMenuItem isDelete(Integer isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public AuthorityMenuItem menuItem(MenuItem menuItem) {
        this.setMenuItem(menuItem);
        return this;
    }

    public Authority getAuthority() {
        return this.authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public AuthorityMenuItem authority(Authority authority) {
        this.setAuthority(authority);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorityMenuItem)) {
            return false;
        }
        return getId() != null && getId().equals(((AuthorityMenuItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorityMenuItem{" +
            "id=" + getId() +
            ", isAllow=" + getIsAllow() +
            ", isRead=" + getIsRead() +
            ", isWrite=" + getIsWrite() +
            ", isDelete=" + getIsDelete() +
            "}";
    }
}
