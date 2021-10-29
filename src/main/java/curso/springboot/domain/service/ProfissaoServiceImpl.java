package curso.springboot.domain.service;

import curso.springboot.domain.model.ProfissaoModel;
import curso.springboot.domain.repository.ProfissaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class ProfissaoServiceImpl implements ProfissaoService {

    private final ProfissaoRepository profissaoRepository;

    public ProfissaoServiceImpl(ProfissaoRepository profissaoRepository) {
        this.profissaoRepository = profissaoRepository;
    }

    @Override
    public Page<ProfissaoModel> findAll() {
        return (Page<ProfissaoModel>) profissaoRepository.findAll();
    }

}
