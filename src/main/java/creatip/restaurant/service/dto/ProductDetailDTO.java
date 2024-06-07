package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.ProductDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDetailDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String size;

    @NotNull
    private BigDecimal dineInPrice;

    @NotNull
    private BigDecimal takeAwayPrice;

    @NotNull
    private BigDecimal deliveryPrice;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getDineInPrice() {
        return dineInPrice;
    }

    public void setDineInPrice(BigDecimal dineInPrice) {
        this.dineInPrice = dineInPrice;
    }

    public BigDecimal getTakeAwayPrice() {
        return takeAwayPrice;
    }

    public void setTakeAwayPrice(BigDecimal takeAwayPrice) {
        this.takeAwayPrice = takeAwayPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDetailDTO)) {
            return false;
        }

        ProductDetailDTO productDetailDTO = (ProductDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetailDTO{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            ", dineInPrice=" + getDineInPrice() +
            ", takeAwayPrice=" + getTakeAwayPrice() +
            ", deliveryPrice=" + getDeliveryPrice() +
            ", product=" + getProduct() +
            "}";
    }
}
