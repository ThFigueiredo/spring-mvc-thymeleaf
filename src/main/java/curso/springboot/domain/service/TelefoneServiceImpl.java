package curso.springboot.domain.service;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.model.TelefoneModel;
import curso.springboot.domain.repository.TelefoneRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TelefoneServiceImpl implements TelefoneService {

    private final TelefoneRepository telefoneRepository;

    public TelefoneServiceImpl(TelefoneRepository telefoneRepository) {
        this.telefoneRepository = telefoneRepository;
    }

    @Override
    public TelefoneModel create(TelefoneModel entity) {
        return telefoneRepository.save(entity);
    }



//    @Override
//    public Page<TelefoneModel> findAll() {
//        return (Page<TelefoneModel>) telefoneRepository.findAll();
//    }

}
