package creatip.restaurant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MenuItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MenuItem getMenuItemSample1() {
        return new MenuItem()
            .id(1L)
            .code("code1")
            .name("name1")
            .translateKey("translateKey1")
            .orderNo(1)
            .faIcon("faIcon1")
            .routerLink("routerLink1");
    }

    public static MenuItem getMenuItemSample2() {
        return new MenuItem()
            .id(2L)
            .code("code2")
            .name("name2")
            .translateKey("translateKey2")
            .orderNo(2)
            .faIcon("faIcon2")
            .routerLink("routerLink2");
    }

    public static MenuItem getMenuItemRandomSampleGenerator() {
        return new MenuItem()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .translateKey(UUID.randomUUID().toString())
            .orderNo(intCount.incrementAndGet())
            .faIcon(UUID.randomUUID().toString())
            .routerLink(UUID.randomUUID().toString());
    }
}
