package curso.springboot.api.controller;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PessoaController {

    @Autowired //anotação autowired
    private PessoaRepository pessoaRepository;

    //SALVAR (MÉTODO DE REDIRECIONAMENTO)
    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public String inicio() { //início = método
        return "cadastro/cadastropessoa";
    }

//    //SALVAR (SALVA REDIRECIONANDO PRA MESMA PASTA)
//    @RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
//    public String salvar(PessoaModel pessoaModel) {
//        pessoaRepository.save(pessoaModel);
//        return "cadastro/cadastropessoa";
//    }
    //SALVAR (SALVA E CARREGA O LISTAR NA MESMA PÁGINA)
    @RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
    public ModelAndView salvar(PessoaModel pessoaModel) {
        pessoaRepository.save(pessoaModel);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIt);

        return andView;
    }

    //LISTAR
    @RequestMapping(method = RequestMethod.GET, value = "/listarpessoas")
    public ModelAndView pessoas() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIt); //pessoas faz interação com thymeleaf
        return andView;
    }
}
