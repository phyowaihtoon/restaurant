package creatip.restaurant.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuTypeDTO.class);
        MenuTypeDTO menuTypeDTO1 = new MenuTypeDTO();
        menuTypeDTO1.setId(1L);
        MenuTypeDTO menuTypeDTO2 = new MenuTypeDTO();
        assertThat(menuTypeDTO1).isNotEqualTo(menuTypeDTO2);
        menuTypeDTO2.setId(menuTypeDTO1.getId());
        assertThat(menuTypeDTO1).isEqualTo(menuTypeDTO2);
        menuTypeDTO2.setId(2L);
        assertThat(menuTypeDTO1).isNotEqualTo(menuTypeDTO2);
        menuTypeDTO1.setId(null);
        assertThat(menuTypeDTO1).isNotEqualTo(menuTypeDTO2);
    }
}
