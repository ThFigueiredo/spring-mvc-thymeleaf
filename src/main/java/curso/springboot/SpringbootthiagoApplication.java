package curso.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;

@SpringBootApplication
@EntityScan(basePackages = "curso.springboot.model") //indica ao aplication que existe stributos do módel a ser inseridos no model
public class SpringbootthiagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootthiagoApplication.class, args);
	}

}
