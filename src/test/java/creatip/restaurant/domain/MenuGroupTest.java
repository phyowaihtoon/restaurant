package creatip.restaurant.domain;

import static creatip.restaurant.domain.MenuGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuGroup.class);
        MenuGroup menuGroup1 = getMenuGroupSample1();
        MenuGroup menuGroup2 = new MenuGroup();
        assertThat(menuGroup1).isNotEqualTo(menuGroup2);

        menuGroup2.setId(menuGroup1.getId());
        assertThat(menuGroup1).isEqualTo(menuGroup2);

        menuGroup2 = getMenuGroupSample2();
        assertThat(menuGroup1).isNotEqualTo(menuGroup2);
    }
}
