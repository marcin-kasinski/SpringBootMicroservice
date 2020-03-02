package mk;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

import io.micrometer.core.instrument.MeterRegistry;
import mk.metrics.SampleMetricBean;
import mk.model.User;
import mk.service.MysqlUserService;
import mk.service.MysqlUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;


@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
//@AutoConfigureMockMvc

@ActiveProfiles("test") // Like this
public class Controller2Test {

    @Autowired
    private MockMvc mockMvc;

/*
    @Bean
    public MysqlUserService mysqlUserService() {
        return new MysqlUserServiceImpl();
    }
*/

    @Autowired
    private MysqlUserService mysqlUserService;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("adding user");

        User user = new User();
        user.setName("XTESTName");
        user.setId(123L);
        user.setEmail("x@x.com");
        mysqlUserService.addUser(user);

        //when(mysqlUserService.findByEmail("x@x.com")         ).thenReturn(user);

        System.out.println("user added");

        System.out.println("finding users");

        //List<User> users=mysqlUserServiceImpl.findAll();
        System.out.println("users found");

        //System.out.println(this.mockMvc.toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //System.out.println("users size: "+users.size());
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+content().str);

        MvcResult result = this.mockMvc.perform(get("/api/get-by-email?email=x@x.com")).andDo(print()).andReturn();
        String content = result.getResponse().getContentAsString();


    }

}
