package creatip.restaurant.domain;

import java.util.UUID;

public class AuthorityTestSamples {

    public static Authority getAuthoritySample1() {
        return new Authority().name("name1").description("description1");
    }

    public static Authority getAuthoritySample2() {
        return new Authority().name("name2").description("description2");
    }

    public static Authority getAuthorityRandomSampleGenerator() {
        return new Authority().name(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
