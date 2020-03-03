package mk;

import mk.dao.MysqlUserRepository;
import mk.model.User;
import mk.service.MysqlUserService;
import mk.service.MysqlUserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class MysqlUserServiceImplIntegrationTest {

    @TestConfiguration
    static class MysqlUserServiceImplTestContextConfiguration {

        @Bean
        public MysqlUserService mysqlUserService() {
            return new MysqlUserServiceImpl();
        }
    }

    @Autowired
    private MysqlUserService mysqlUserService;

    @MockBean
    private MysqlUserRepository mysqlUserRepository;


    @Before
    public void setUp() {

        User user = new User();
        user.setName("XTESTName");
        user.setId(123L);
        user.setEmail("x@x.com");


        Mockito.when(mysqlUserRepository.findByEmail(user.getEmail()))
                .thenReturn(user);
    }

    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        String email = "x@x.com";
        User user = mysqlUserService.findByEmail(email);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("adding user");

        assertThat(user.getEmail()).isEqualTo(email);

    }
}