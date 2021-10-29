package curso.springboot.domain.repository;

import curso.springboot.domain.model.TelefoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<TelefoneModel, Long> {
//    @Query("select  t from TelefoneModel t where t.pessoaModel.id = ?1")
//    List<TelefoneModel> getTelefones(Long pessoaid);
    List<TelefoneModel> findAllByPessoa_Id(Long id);

}
