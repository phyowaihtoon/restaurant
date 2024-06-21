package creatip.restaurant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MenuGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MenuGroup getMenuGroupSample1() {
        return new MenuGroup()
            .id(1L)
            .code("code1")
            .name("name1")
            .isChild(1)
            .parentId(1L)
            .translateKey("translateKey1")
            .orderNo(1)
            .faIcon("faIcon1")
            .routerLink("routerLink1");
    }

    public static MenuGroup getMenuGroupSample2() {
        return new MenuGroup()
            .id(2L)
            .code("code2")
            .name("name2")
            .isChild(2)
            .parentId(2L)
            .translateKey("translateKey2")
            .orderNo(2)
            .faIcon("faIcon2")
            .routerLink("routerLink2");
    }

    public static MenuGroup getMenuGroupRandomSampleGenerator() {
        return new MenuGroup()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .isChild(intCount.incrementAndGet())
            .parentId(longCount.incrementAndGet())
            .translateKey(UUID.randomUUID().toString())
            .orderNo(intCount.incrementAndGet())
            .faIcon(UUID.randomUUID().toString())
            .routerLink(UUID.randomUUID().toString());
    }
}
