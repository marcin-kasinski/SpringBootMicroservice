package mk;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.micrometer.core.instrument.MeterRegistry;
import mk.dao.MongoUserRepository;
import mk.dao.MysqlUserRepository;
import mk.dao.UserNumRepository;
import mk.metrics.SampleMetricBean;
import mk.service.UserNumService;


@RunWith(SpringRunner.class)
//@ContextConfiguration(initializers = TestConfigInitailizer.class)
@WebMvcTest(Microservice.class) 
//@SpringBootTest
//@DataJpaTest
public class WebControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private UserNumService userNumService;
	@MockBean
	private SampleMetricBean sampleBean;
	@MockBean
	MeterRegistry registry;
	@MockBean
	private MysqlUserRepository mysqlUserRepository;
	@MockBean
	private MongoUserRepository mongoUserRepository;


	@MockBean
	private UserNumRepository userNumRepository;

	

//	@LocalServerPort
//	private int port;

	@Test
	public void returnDefaultMessageWithoutStartingServer() throws Exception {

		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		System.out.println(methodName + " test started");

//		String url = "http://localhost:" + port + "/api/get-by-email?email=x@x.com";
		String url =  "/api/get-by-email?email=x@x.com";
		String expected_output = "{\"id\":1,\"name\":\"Marcin\",\"email\":\"x@x.com\"}";

		//this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(expected_output)));

		if (this.mockMvc==null) System.out.println("this.mockMvc is null");
		else if (this.mockMvc!=null) System.out.println("this.mockMvc is not null");

		//MockHttpServletRequestBuilder mockHttpServletRequestBuilder=MockMvcRequestBuilders.get(url);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder=get(url);
		
		if (mockHttpServletRequestBuilder==null) System.out.println("mockHttpServletRequestBuilder is null");
		else if (mockHttpServletRequestBuilder!=null) System.out.println("mockHttpServletRequestBuilder is not null");
		
		
		this.mockMvc.perform(mockHttpServletRequestBuilder);

		System.out.println(methodName + " test executed");

	}


//	@Configuration
 //   @ComponentScan(basePackageClasses = { Microservice.class })
 //   public static class TestConf {}	
	
	
	
}
