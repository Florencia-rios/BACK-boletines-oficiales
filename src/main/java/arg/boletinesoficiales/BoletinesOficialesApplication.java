package arg.boletinesoficiales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.naming.NamingException;

@SpringBootApplication
public class BoletinesOficialesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoletinesOficialesApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() throws NamingException {
        return new RestTemplate();
    }

}
