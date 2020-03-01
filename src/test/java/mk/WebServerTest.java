package mk;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
//import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mk.dao.MysqlUserRepository;
import mk.model.User;
import mk.service.MysqlUserServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

//@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(initializers = TestConfigInitailizer.class)

public class WebServerTest {
/*
	@Autowired
	private MysqlUserRepository mysqlUserRepository;

	@Test
	public void givenStudent_whenSave_thenGetOk() {

		User user = new User();
		user.setName("john");
		mysqlUserRepository.save(user);

		//User user2 = mysqlUserRepository.findOne((Long) 1);
		assertEquals("john", user.getName());




	}
*/

/*
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MicroserviceController controller;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

//	@Test
	public void contextLoads() throws Exception {

		System.out.println("contextLoads test started");

		assertThat(controller).isNotNull();

		System.out.println("contextLoads test executed");
	}

	@Test
	public void returnDefaultMessage() throws Exception {

		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		System.out.println(methodName + " test started");

		String url = "http://localhost:" + port + "/api/get-by-email?email=x@x.com";
		String expected_output = "{\"id\":1,\"name\":\"Marcin\",\"email\":\"x@x.com\"}";

		String got_output = this.restTemplate.getForObject(url, String.class);

		System.out.println("expected output:" + expected_output);
		System.out.println("got output:" + got_output);

//      assertThat(this.restTemplate.getForObject(url,String.class)).contains(expected_output);
		assertThat(got_output.equals(expected_output));

		System.out.println(methodName + " test executed");

	}
*/
}
