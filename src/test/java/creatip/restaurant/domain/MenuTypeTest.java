package creatip.restaurant.domain;

import static creatip.restaurant.domain.MenuTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuType.class);
        MenuType menuType1 = getMenuTypeSample1();
        MenuType menuType2 = new MenuType();
        assertThat(menuType1).isNotEqualTo(menuType2);

        menuType2.setId(menuType1.getId());
        assertThat(menuType1).isEqualTo(menuType2);

        menuType2 = getMenuTypeSample2();
        assertThat(menuType1).isNotEqualTo(menuType2);
    }
}
