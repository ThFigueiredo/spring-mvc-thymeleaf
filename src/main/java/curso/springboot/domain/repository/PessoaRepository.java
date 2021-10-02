package curso.springboot.domain.repository;

import curso.springboot.domain.model.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
    //FAZENDO A PESQUISA POR NOME
    @Query("select p from PessoaModel p where p.nome like %?1%")
    //@Query("select p from PessoaModel p where p.nome like %?1% and %?2%") -> passando mais de 1 parametro
    List<PessoaModel> findPessoaModelByName(String nome);

}
