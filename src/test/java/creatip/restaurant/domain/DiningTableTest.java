package creatip.restaurant.domain;

import static creatip.restaurant.domain.DiningTableTestSamples.*;
import static creatip.restaurant.domain.ReservationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import creatip.restaurant.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DiningTableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiningTable.class);
        DiningTable diningTable1 = getDiningTableSample1();
        DiningTable diningTable2 = new DiningTable();
        assertThat(diningTable1).isNotEqualTo(diningTable2);

        diningTable2.setId(diningTable1.getId());
        assertThat(diningTable1).isEqualTo(diningTable2);

        diningTable2 = getDiningTableSample2();
        assertThat(diningTable1).isNotEqualTo(diningTable2);
    }

    @Test
    void reservationsTest() throws Exception {
        DiningTable diningTable = getDiningTableRandomSampleGenerator();
        Reservation reservationBack = getReservationRandomSampleGenerator();

        diningTable.addReservations(reservationBack);
        assertThat(diningTable.getReservations()).containsOnly(reservationBack);
        assertThat(reservationBack.getDiningTables()).containsOnly(diningTable);

        diningTable.removeReservations(reservationBack);
        assertThat(diningTable.getReservations()).doesNotContain(reservationBack);
        assertThat(reservationBack.getDiningTables()).doesNotContain(diningTable);

        diningTable.reservations(new HashSet<>(Set.of(reservationBack)));
        assertThat(diningTable.getReservations()).containsOnly(reservationBack);
        assertThat(reservationBack.getDiningTables()).containsOnly(diningTable);

        diningTable.setReservations(new HashSet<>());
        assertThat(diningTable.getReservations()).doesNotContain(reservationBack);
        assertThat(reservationBack.getDiningTables()).doesNotContain(diningTable);
    }
}
