package mk;

//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import mk.dao.MysqlUserRepository;
import mk.model.User;

//@RunWith(SpringRunner.class)
//@DataJpaTest
public class WebControllerIntegrationTest {
/*
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MysqlUserRepository mysqlUserRepository;

	@Test
	public void testMysqlReplyIntegrationTest() throws Exception {

		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		System.out.println(methodName + " test started");

		User alex = new User("alex#xxx", "alex");
		entityManager.persist(alex);
		entityManager.flush();

		// when
		User found = mysqlUserRepository.findByEmail(alex.getEmail());
		// then

		assertThat(found.getEmail()).isEqualTo(alex.getEmail()

		);

		System.out.println("Found found" + found.getName());

		System.out.println(methodName + " test executed");

	}
*/
}
