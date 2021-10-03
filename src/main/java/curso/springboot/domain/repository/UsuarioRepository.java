package curso.springboot.domain.repository;

import curso.springboot.domain.model.UsuarioModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
	
	@Query("select u from UsuarioModel u where u.login = ?1")
	UsuarioModel findUserByLogin(String login);

}
