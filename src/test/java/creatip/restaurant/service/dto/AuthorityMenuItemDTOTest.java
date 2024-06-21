package creatip.restaurant.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorityMenuItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorityMenuItemDTO.class);
        AuthorityMenuItemDTO authorityMenuItemDTO1 = new AuthorityMenuItemDTO();
        authorityMenuItemDTO1.setId(1L);
        AuthorityMenuItemDTO authorityMenuItemDTO2 = new AuthorityMenuItemDTO();
        assertThat(authorityMenuItemDTO1).isNotEqualTo(authorityMenuItemDTO2);
        authorityMenuItemDTO2.setId(authorityMenuItemDTO1.getId());
        assertThat(authorityMenuItemDTO1).isEqualTo(authorityMenuItemDTO2);
        authorityMenuItemDTO2.setId(2L);
        assertThat(authorityMenuItemDTO1).isNotEqualTo(authorityMenuItemDTO2);
        authorityMenuItemDTO1.setId(null);
        assertThat(authorityMenuItemDTO1).isNotEqualTo(authorityMenuItemDTO2);
    }
}
