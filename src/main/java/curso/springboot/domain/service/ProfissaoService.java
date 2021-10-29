package curso.springboot.domain.service;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.model.ProfissaoModel;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfissaoService {
    public Page<ProfissaoModel> findAll();
}
