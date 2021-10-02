package curso.springboot.api.controller;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired //anotação autowired
    private PessoaRepository pessoaRepository;

    //SALVAR (MÉTODO DE REDIRECIONAMENTO)
    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio() { //início = método
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", new PessoaModel());
        return modelAndView;
    }

//    //SALVAR (SALVA REDIRECIONANDO PRA MESMA PASTA)
//    @RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
//    public String salvar(PessoaModel pessoaModel) {
//        pessoaRepository.save(pessoaModel);
//        return "cadastro/cadastropessoa";
//    }
    //SALVAR (SALVA E CARREGA O LISTAR NA MESMA PÁGINA)
    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa") //** ignora o que vem antes na url
    public ModelAndView salvar(PessoaModel pessoaModel) {
        pessoaRepository.save(pessoaModel);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIt);
        andView.addObject("pessoaobj", new PessoaModel()); //passando objeto vazio

        return andView;
    }
    //LISTAR
    @RequestMapping(method = RequestMethod.GET, value = "/listarpessoas")
    public ModelAndView pessoas() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIt); //pessoas faz interação com thymeleaf
        andView.addObject("pessoaobj", new PessoaModel());

        return andView;
    }

    @GetMapping("/editarpessoa/{idpessoa}") //GetMapping subistitui o @RequestMapping acima
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {//idpessoas faz interação com thymeleaf

        Optional<PessoaModel> pessoaModel = pessoaRepository.findById(idpessoa); //instanciando o objeto pessoa

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", pessoaModel.get());
        return modelAndView;

    }
}
