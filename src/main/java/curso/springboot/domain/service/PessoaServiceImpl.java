package curso.springboot.domain.service;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.model.TelefoneModel;
import curso.springboot.domain.repository.PessoaRepository;
import curso.springboot.domain.repository.TelefoneRepository;
import javassist.NotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final TelefoneRepository telefoneRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository, TelefoneRepository telefoneRepository) {
        this.pessoaRepository = pessoaRepository;
        this.telefoneRepository = telefoneRepository;
    }


    @Override
    public PessoaModel create(PessoaModel entity) {
        return pessoaRepository.save(entity);
    }

    @Override
    public PessoaModel findById(Long id) throws NotFoundException {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organização não encontrada"));
    }

    //    @Override
//    public Page<PessoaModel> findAll(Pageable pageable) {
//        List<PessoaModel> example = repository.findAllByNomeLike("aa%");
//        return repository.findAll(pageable);
//    }

    public Page<PessoaModel> findAll(Pageable pageable) {
        //List<PessoaModel> example = repository.findAllByNomeLike("aa%"); // findAllByNomeLike essa consulta foi criada em reposítory
        return pessoaRepository.findAll(pageable);
    }

    @Override
    public void update(Long id, PessoaModel entity) throws NotFoundException {
        PessoaModel exampleModel = findById(id);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        mapper.map(entity, exampleModel);

        pessoaRepository.save(exampleModel);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        PessoaModel pessoaModel = findById(id);
        pessoaRepository.delete(pessoaModel);
    }

    @Override
    public PessoaModel setTelefones(Long idPessoa) throws NotFoundException {
        PessoaModel pessoaModel = findById(idPessoa);
        List<TelefoneModel> telefones = telefoneRepository.findAllByPessoa_Id(idPessoa);
        pessoaModel.setTelefones(telefones);
        return pessoaModel;
    }

    //PAGINAÇÃO
    @Override
    public Page<PessoaModel> findPaginated(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Page<PessoaModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.pessoaRepository.findAll(pageable);
    }
}
