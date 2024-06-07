package creatip.restaurant.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddOnItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddOnItemDTO.class);
        AddOnItemDTO addOnItemDTO1 = new AddOnItemDTO();
        addOnItemDTO1.setId(1L);
        AddOnItemDTO addOnItemDTO2 = new AddOnItemDTO();
        assertThat(addOnItemDTO1).isNotEqualTo(addOnItemDTO2);
        addOnItemDTO2.setId(addOnItemDTO1.getId());
        assertThat(addOnItemDTO1).isEqualTo(addOnItemDTO2);
        addOnItemDTO2.setId(2L);
        assertThat(addOnItemDTO1).isNotEqualTo(addOnItemDTO2);
        addOnItemDTO1.setId(null);
        assertThat(addOnItemDTO1).isNotEqualTo(addOnItemDTO2);
    }
}
