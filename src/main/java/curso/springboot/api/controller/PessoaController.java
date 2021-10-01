package curso.springboot.api.controller;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PessoaController {

    @Autowired //anotação autowired
    private PessoaRepository pessoaRepository;

    //SALVAR (MÉTODO DE REDIRECIONAMENTO)
    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public String inicio(){ //início = método
        return "cadastro/cadastropessoa";
    }

    //SALVAR (SALVA REDIRECIONANDO PRA MESMA PASTA)
    @RequestMapping(method=RequestMethod.POST, value="/salvarpessoa")
    public String salvar(PessoaModel pessoaModel) {
        pessoaRepository.save(pessoaModel);
        return "cadastro/cadastropessoa";
    }


}
