package creatip.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservation extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "reserved_date")
    private Instant reservedDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "reserved_dining_table",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "dining_table_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservations" }, allowSetters = true)
    private Set<DiningTable> diningTables = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Reservation name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public Reservation phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getReservedDate() {
        return this.reservedDate;
    }

    public Reservation reservedDate(Instant reservedDate) {
        this.setReservedDate(reservedDate);
        return this;
    }

    public void setReservedDate(Instant reservedDate) {
        this.reservedDate = reservedDate;
    }

    public Set<DiningTable> getDiningTables() {
        return this.diningTables;
    }

    public void setDiningTables(Set<DiningTable> diningTables) {
        this.diningTables = diningTables;
    }

    public Reservation diningTables(Set<DiningTable> diningTables) {
        this.setDiningTables(diningTables);
        return this;
    }

    public Reservation addDiningTables(DiningTable diningTable) {
        this.diningTables.add(diningTable);
        return this;
    }

    public Reservation removeDiningTables(DiningTable diningTable) {
        this.diningTables.remove(diningTable);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return getId() != null && getId().equals(((Reservation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", reservedDate='" + getReservedDate() + "'" +
            "}";
    }
}
