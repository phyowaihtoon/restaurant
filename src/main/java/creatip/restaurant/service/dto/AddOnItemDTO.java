package creatip.restaurant.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link creatip.restaurant.domain.AddOnItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddOnItemDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String itemName;

    @NotNull
    private BigDecimal price;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        if (!(o instanceof AddOnItemDTO)) {
            return false;
        }

        AddOnItemDTO addOnItemDTO = (AddOnItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addOnItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddOnItemDTO{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", price=" + getPrice() +
            ", product=" + getProduct() +
            "}";
    }
}
