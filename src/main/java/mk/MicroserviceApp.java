package mk;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.consul.ConsulAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;  



/*


W consul generujemy property:


config/SpringBootMicroservice/props


a w nim :

server.port=6661



CREATE DATABASE test;
use test;
	
	CREATE TABLE `users` (
	  `id` bigint(20) NOT NULL,
	  `email` varchar(255) DEFAULT NULL,
	  `name` varchar(255) DEFAULT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	
	
	INSERT INTO users (`id`,`email`, `name`) VALUES (1 , 'x@x.com','Marcin');
	INSERT INTO users (`id`,`email`, `name`) VALUES (2 , 'z@fromlistener.com','Marcin from listener');

*/

//@EnableDiscoveryClient
@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration(exclude = {ConsulAutoConfiguration.class})
//@EnableJpaRepositories(basePackages = {"mk"})
public class MicroserviceApp {

//	@Value("${spring.cloud.consul.discovery.instanceId}")
//	public static String appId;
	
	public static void main(String[] args) {


		System.out.println("MK7 MicroserviceApp ver XXX");

		
		/*

System.out.println("MK6 MicroserviceApp ENV variables");


Map<String, String> env = System.getenv();
for (String envName : env.keySet()) {
    System.out.format("%s=%s%n", envName, env.get(envName));
}

*/
		
		SpringApplication.run(MicroserviceApp.class, args);
		}
	

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/findallusersbyemail").allowedOrigins("http://localhost:8080");
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }  
	
}
