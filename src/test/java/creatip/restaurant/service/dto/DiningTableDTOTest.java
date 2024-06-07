package creatip.restaurant.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiningTableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiningTableDTO.class);
        DiningTableDTO diningTableDTO1 = new DiningTableDTO();
        diningTableDTO1.setId(1L);
        DiningTableDTO diningTableDTO2 = new DiningTableDTO();
        assertThat(diningTableDTO1).isNotEqualTo(diningTableDTO2);
        diningTableDTO2.setId(diningTableDTO1.getId());
        assertThat(diningTableDTO1).isEqualTo(diningTableDTO2);
        diningTableDTO2.setId(2L);
        assertThat(diningTableDTO1).isNotEqualTo(diningTableDTO2);
        diningTableDTO1.setId(null);
        assertThat(diningTableDTO1).isNotEqualTo(diningTableDTO2);
    }
}
