package creatip.restaurant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .productCode("productCode1")
            .productName("productName1")
            .imageURL("imageURL1")
            .description("description1")
            .status(1);
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .productCode("productCode2")
            .productName("productName2")
            .imageURL("imageURL2")
            .description("description2")
            .status(2);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .productCode(UUID.randomUUID().toString())
            .productName(UUID.randomUUID().toString())
            .imageURL(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .status(intCount.incrementAndGet());
    }
}
