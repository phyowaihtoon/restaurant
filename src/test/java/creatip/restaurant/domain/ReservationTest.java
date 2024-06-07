package creatip.restaurant.domain;

import static creatip.restaurant.domain.DiningTableTestSamples.*;
import static creatip.restaurant.domain.ReservationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservation.class);
        Reservation reservation1 = getReservationSample1();
        Reservation reservation2 = new Reservation();
        assertThat(reservation1).isNotEqualTo(reservation2);

        reservation2.setId(reservation1.getId());
        assertThat(reservation1).isEqualTo(reservation2);

        reservation2 = getReservationSample2();
        assertThat(reservation1).isNotEqualTo(reservation2);
    }

    @Test
    void diningTablesTest() throws Exception {
        Reservation reservation = getReservationRandomSampleGenerator();
        DiningTable diningTableBack = getDiningTableRandomSampleGenerator();

        reservation.addDiningTables(diningTableBack);
        assertThat(reservation.getDiningTables()).containsOnly(diningTableBack);

        reservation.removeDiningTables(diningTableBack);
        assertThat(reservation.getDiningTables()).doesNotContain(diningTableBack);

        reservation.diningTables(new HashSet<>(Set.of(diningTableBack)));
        assertThat(reservation.getDiningTables()).containsOnly(diningTableBack);

        reservation.setDiningTables(new HashSet<>());
        assertThat(reservation.getDiningTables()).doesNotContain(diningTableBack);
    }
}
