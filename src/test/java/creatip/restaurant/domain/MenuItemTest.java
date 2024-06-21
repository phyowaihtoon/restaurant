package creatip.restaurant.domain;

import static creatip.restaurant.domain.MenuGroupTestSamples.*;
import static creatip.restaurant.domain.MenuItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuItem.class);
        MenuItem menuItem1 = getMenuItemSample1();
        MenuItem menuItem2 = new MenuItem();
        assertThat(menuItem1).isNotEqualTo(menuItem2);

        menuItem2.setId(menuItem1.getId());
        assertThat(menuItem1).isEqualTo(menuItem2);

        menuItem2 = getMenuItemSample2();
        assertThat(menuItem1).isNotEqualTo(menuItem2);
    }

    @Test
    void menuGroupTest() throws Exception {
        MenuItem menuItem = getMenuItemRandomSampleGenerator();
        MenuGroup menuGroupBack = getMenuGroupRandomSampleGenerator();

        menuItem.setMenuGroup(menuGroupBack);
        assertThat(menuItem.getMenuGroup()).isEqualTo(menuGroupBack);

        menuItem.menuGroup(null);
        assertThat(menuItem.getMenuGroup()).isNull();
    }
}
