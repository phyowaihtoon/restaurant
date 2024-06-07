package creatip.restaurant.domain;

import static creatip.restaurant.domain.ProductDetailTestSamples.*;
import static creatip.restaurant.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDetail.class);
        ProductDetail productDetail1 = getProductDetailSample1();
        ProductDetail productDetail2 = new ProductDetail();
        assertThat(productDetail1).isNotEqualTo(productDetail2);

        productDetail2.setId(productDetail1.getId());
        assertThat(productDetail1).isEqualTo(productDetail2);

        productDetail2 = getProductDetailSample2();
        assertThat(productDetail1).isNotEqualTo(productDetail2);
    }

    @Test
    void productTest() throws Exception {
        ProductDetail productDetail = getProductDetailRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        productDetail.setProduct(productBack);
        assertThat(productDetail.getProduct()).isEqualTo(productBack);

        productDetail.product(null);
        assertThat(productDetail.getProduct()).isNull();
    }
}
