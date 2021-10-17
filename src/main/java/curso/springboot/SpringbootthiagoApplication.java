package curso.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import javax.persistence.Entity;

@SpringBootApplication
@EntityScan(basePackages = "curso/springboot/domain/model") //passa o caminho do módel
//@ComponentScan(basePackages = {"curso.*"}) mapeia todas as pastas / nao precisei neste projeto
@EnableWebMvc
public class SpringbootthiagoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootthiagoApplication.class, args);
	}

	//@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/login").setViewName("/login");
//		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
//	}
}
