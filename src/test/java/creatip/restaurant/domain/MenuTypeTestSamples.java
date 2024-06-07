package creatip.restaurant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MenuTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MenuType getMenuTypeSample1() {
        return new MenuType().id(1L).name("name1");
    }

    public static MenuType getMenuTypeSample2() {
        return new MenuType().id(2L).name("name2");
    }

    public static MenuType getMenuTypeRandomSampleGenerator() {
        return new MenuType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
