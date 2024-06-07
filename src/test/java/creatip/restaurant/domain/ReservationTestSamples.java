package creatip.restaurant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Reservation getReservationSample1() {
        return new Reservation().id(1L).name("name1").phone("phone1");
    }

    public static Reservation getReservationSample2() {
        return new Reservation().id(2L).name("name2").phone("phone2");
    }

    public static Reservation getReservationRandomSampleGenerator() {
        return new Reservation().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).phone(UUID.randomUUID().toString());
    }
}
