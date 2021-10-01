package curso.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

//import javax.persistence.Entity;

@SpringBootApplication
@EntityScan(basePackages = "curso/springboot/domain/model") //passa o caminho do módel
//@ComponentScan(basePackages = {"curso.*"}) mapeia todas as pastas / nao precisei neste projeto
public class SpringbootthiagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootthiagoApplication.class, args);
	}

}
