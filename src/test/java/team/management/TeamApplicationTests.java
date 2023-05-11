package team.management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class TeamApplicationTests {

	@Test
	void contextLoads() {
		ConfigurableApplicationContext context = SpringApplication.run(TeamApplication.class);
		Assertions.assertNotNull(context);
		context.close();
	}

}
