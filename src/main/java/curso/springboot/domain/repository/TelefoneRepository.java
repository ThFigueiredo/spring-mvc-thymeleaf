package curso.springboot.domain.repository;

import curso.springboot.domain.model.TelefoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<TelefoneModel, Long> {
}
