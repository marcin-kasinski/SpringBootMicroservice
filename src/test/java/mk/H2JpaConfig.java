package mk;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableJpaRepositories(basePackages = "org.baeldung.repository")
//@ActiveProfiles("test") // Like this
//@EnableTransactionManagement
public class H2JpaConfig {
}
