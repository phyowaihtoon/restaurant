package creatip.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductDetail.
 */
@Entity
@Table(name = "product_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDetail extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "size", length = 255, nullable = false)
    private String size;

    @NotNull
    @Column(name = "dine_in_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal dineInPrice;

    @NotNull
    @Column(name = "take_away_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal takeAwayPrice;

    @NotNull
    @Column(name = "delivery_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal deliveryPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return this.size;
    }

    public ProductDetail size(String size) {
        this.setSize(size);
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getDineInPrice() {
        return this.dineInPrice;
    }

    public ProductDetail dineInPrice(BigDecimal dineInPrice) {
        this.setDineInPrice(dineInPrice);
        return this;
    }

    public void setDineInPrice(BigDecimal dineInPrice) {
        this.dineInPrice = dineInPrice;
    }

    public BigDecimal getTakeAwayPrice() {
        return this.takeAwayPrice;
    }

    public ProductDetail takeAwayPrice(BigDecimal takeAwayPrice) {
        this.setTakeAwayPrice(takeAwayPrice);
        return this;
    }

    public void setTakeAwayPrice(BigDecimal takeAwayPrice) {
        this.takeAwayPrice = takeAwayPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public ProductDetail deliveryPrice(BigDecimal deliveryPrice) {
        this.setDeliveryPrice(deliveryPrice);
        return this;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDetail product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((ProductDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetail{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            ", dineInPrice=" + getDineInPrice() +
            ", takeAwayPrice=" + getTakeAwayPrice() +
            ", deliveryPrice=" + getDeliveryPrice() +
            "}";
    }
}
