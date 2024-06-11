package creatip.restaurant;

import creatip.restaurant.config.AsyncSyncConfiguration;
import creatip.restaurant.config.EmbeddedSQL;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base composite annotation for integration tests.
 */
//@ActiveProfiles({"testcontainers"}) // to run two profiles {"dev","testcontainers"}
//@SpringBootTest(classes = { RestaurantApp.class, AsyncSyncConfiguration.class })
//@EmbeddedSQL
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { RestaurantApp.class })
public @interface IntegrationTest {
}
