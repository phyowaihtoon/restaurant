package creatip.restaurant.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DiningTableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DiningTable getDiningTableSample1() {
        return new DiningTable().id(1L).tableNumber(1).seatingCapacity(1).tableStatus(1);
    }

    public static DiningTable getDiningTableSample2() {
        return new DiningTable().id(2L).tableNumber(2).seatingCapacity(2).tableStatus(2);
    }

    public static DiningTable getDiningTableRandomSampleGenerator() {
        return new DiningTable()
            .id(longCount.incrementAndGet())
            .tableNumber(intCount.incrementAndGet())
            .seatingCapacity(intCount.incrementAndGet())
            .tableStatus(intCount.incrementAndGet());
    }
}
