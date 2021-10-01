package curso.springboot.domain.repository;

import curso.springboot.domain.model.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
}
