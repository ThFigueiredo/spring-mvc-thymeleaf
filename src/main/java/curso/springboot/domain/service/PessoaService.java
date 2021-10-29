package curso.springboot.domain.service;

import curso.springboot.domain.model.PessoaModel;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Exemplo de interface com servicos a mais a BaseService
public interface PessoaService  {
    PessoaModel create(PessoaModel entity);
    PessoaModel findById(Long id) throws NotFoundException;
    Page<PessoaModel> findAll(Pageable pageable);
    void update(Long id, PessoaModel entity) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    PessoaModel setTelefones(Long id) throws NotFoundException;

    //PAGINAÇÃO
    Page<PessoaModel> findPaginated(int pageNo, int pageSize);
    Page<PessoaModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);


}
