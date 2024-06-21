package creatip.restaurant.domain;

import static creatip.restaurant.domain.AuthorityMenuItemTestSamples.*;
import static creatip.restaurant.domain.AuthorityTestSamples.*;
import static creatip.restaurant.domain.MenuItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorityMenuItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorityMenuItem.class);
        AuthorityMenuItem authorityMenuItem1 = getAuthorityMenuItemSample1();
        AuthorityMenuItem authorityMenuItem2 = new AuthorityMenuItem();
        assertThat(authorityMenuItem1).isNotEqualTo(authorityMenuItem2);

        authorityMenuItem2.setId(authorityMenuItem1.getId());
        assertThat(authorityMenuItem1).isEqualTo(authorityMenuItem2);

        authorityMenuItem2 = getAuthorityMenuItemSample2();
        assertThat(authorityMenuItem1).isNotEqualTo(authorityMenuItem2);
    }

    @Test
    void menuItemTest() throws Exception {
        AuthorityMenuItem authorityMenuItem = getAuthorityMenuItemRandomSampleGenerator();
        MenuItem menuItemBack = getMenuItemRandomSampleGenerator();

        authorityMenuItem.setMenuItem(menuItemBack);
        assertThat(authorityMenuItem.getMenuItem()).isEqualTo(menuItemBack);

        authorityMenuItem.menuItem(null);
        assertThat(authorityMenuItem.getMenuItem()).isNull();
    }

    @Test
    void authorityTest() throws Exception {
        AuthorityMenuItem authorityMenuItem = getAuthorityMenuItemRandomSampleGenerator();
        Authority authorityBack = getAuthorityRandomSampleGenerator();

        authorityMenuItem.setAuthority(authorityBack);
        assertThat(authorityMenuItem.getAuthority()).isEqualTo(authorityBack);

        authorityMenuItem.authority(null);
        assertThat(authorityMenuItem.getAuthority()).isNull();
    }
}
