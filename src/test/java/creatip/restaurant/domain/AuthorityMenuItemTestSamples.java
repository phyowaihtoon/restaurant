package creatip.restaurant.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AuthorityMenuItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AuthorityMenuItem getAuthorityMenuItemSample1() {
        return new AuthorityMenuItem().id(1L).isAllow(1).isRead(1).isWrite(1).isDelete(1);
    }

    public static AuthorityMenuItem getAuthorityMenuItemSample2() {
        return new AuthorityMenuItem().id(2L).isAllow(2).isRead(2).isWrite(2).isDelete(2);
    }

    public static AuthorityMenuItem getAuthorityMenuItemRandomSampleGenerator() {
        return new AuthorityMenuItem()
            .id(longCount.incrementAndGet())
            .isAllow(intCount.incrementAndGet())
            .isRead(intCount.incrementAndGet())
            .isWrite(intCount.incrementAndGet())
            .isDelete(intCount.incrementAndGet());
    }
}
