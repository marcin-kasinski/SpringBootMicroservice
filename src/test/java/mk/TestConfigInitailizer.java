package mk;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class TestConfigInitailizer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {

		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,"spring.datasource.url=jdbc:mysql://localhost:3306/test");

	}

}
