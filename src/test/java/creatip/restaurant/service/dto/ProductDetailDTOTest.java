package creatip.restaurant.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDetailDTO.class);
        ProductDetailDTO productDetailDTO1 = new ProductDetailDTO();
        productDetailDTO1.setId(1L);
        ProductDetailDTO productDetailDTO2 = new ProductDetailDTO();
        assertThat(productDetailDTO1).isNotEqualTo(productDetailDTO2);
        productDetailDTO2.setId(productDetailDTO1.getId());
        assertThat(productDetailDTO1).isEqualTo(productDetailDTO2);
        productDetailDTO2.setId(2L);
        assertThat(productDetailDTO1).isNotEqualTo(productDetailDTO2);
        productDetailDTO1.setId(null);
        assertThat(productDetailDTO1).isNotEqualTo(productDetailDTO2);
    }
}
