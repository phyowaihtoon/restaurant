package creatip.restaurant.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuGroupDTO.class);
        MenuGroupDTO menuGroupDTO1 = new MenuGroupDTO();
        menuGroupDTO1.setId(1L);
        MenuGroupDTO menuGroupDTO2 = new MenuGroupDTO();
        assertThat(menuGroupDTO1).isNotEqualTo(menuGroupDTO2);
        menuGroupDTO2.setId(menuGroupDTO1.getId());
        assertThat(menuGroupDTO1).isEqualTo(menuGroupDTO2);
        menuGroupDTO2.setId(2L);
        assertThat(menuGroupDTO1).isNotEqualTo(menuGroupDTO2);
        menuGroupDTO1.setId(null);
        assertThat(menuGroupDTO1).isNotEqualTo(menuGroupDTO2);
    }
}
