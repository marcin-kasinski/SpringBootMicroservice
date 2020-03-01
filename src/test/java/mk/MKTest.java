package mk;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import mk.model.User;
import mk.service.MysqlUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Like this
public class MKTest {

    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    //MysqlUserServiceImpl mysqlUserServiceImpl;
    @Mock
    MysqlUserServiceImpl mysqlUserServiceImpl;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));


        /*
        User user = new User();
        user.setName("X");
        user.setEmail("x@x.com");
        mysqlUserServiceImpl.addUser(user);
         */

        System.out.println(this.mockMvc.toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+content().str);

        //this.mockMvc.perform(get("/api/get-by-email?email=x@x.com")).andDo(print());
        MvcResult result = this.mockMvc.perform(get("/api/get-by-email?email=x@x.com")).andDo(print()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Content: "+content);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        /*

        MvcResult result = mockMvc.perform(post("/api/users").header("Authorization", base64ForTestUser).contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"testUserDetails\",\"firstName\":\"xxx\",\"lastName\":\"xxx\",\"password\":\"xxx\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
*/

    }

}
