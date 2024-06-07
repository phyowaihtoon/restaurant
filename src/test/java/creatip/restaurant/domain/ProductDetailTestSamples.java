package creatip.restaurant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductDetailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProductDetail getProductDetailSample1() {
        return new ProductDetail().id(1L).size("size1");
    }

    public static ProductDetail getProductDetailSample2() {
        return new ProductDetail().id(2L).size("size2");
    }

    public static ProductDetail getProductDetailRandomSampleGenerator() {
        return new ProductDetail().id(longCount.incrementAndGet()).size(UUID.randomUUID().toString());
    }
}
