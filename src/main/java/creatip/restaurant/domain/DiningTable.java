package creatip.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DiningTable.
 */
@Entity
@Table(name = "dining_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiningTable extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @NotNull
    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;

    @NotNull
    @Column(name = "table_status", nullable = false)
    private Integer tableStatus;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "diningTables")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diningTables" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DiningTable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return this.tableNumber;
    }

    public DiningTable tableNumber(Integer tableNumber) {
        this.setTableNumber(tableNumber);
        return this;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getSeatingCapacity() {
        return this.seatingCapacity;
    }

    public DiningTable seatingCapacity(Integer seatingCapacity) {
        this.setSeatingCapacity(seatingCapacity);
        return this;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Integer getTableStatus() {
        return this.tableStatus;
    }

    public DiningTable tableStatus(Integer tableStatus) {
        this.setTableStatus(tableStatus);
        return this;
    }

    public void setTableStatus(Integer tableStatus) {
        this.tableStatus = tableStatus;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.removeDiningTables(this));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.addDiningTables(this));
        }
        this.reservations = reservations;
    }

    public DiningTable reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public DiningTable addReservations(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.getDiningTables().add(this);
        return this;
    }

    public DiningTable removeReservations(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.getDiningTables().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiningTable)) {
            return false;
        }
        return getId() != null && getId().equals(((DiningTable) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiningTable{" +
            "id=" + getId() +
            ", tableNumber=" + getTableNumber() +
            ", seatingCapacity=" + getSeatingCapacity() +
            ", tableStatus=" + getTableStatus() +
            "}";
    }
}
