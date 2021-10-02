package curso.springboot.api.controller;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired //anotação autowired
    private PessoaRepository pessoaRepository;

    //SALVAR (MÉTODO DE REDIRECIONAMENTO)
    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio() { //início = nome do método
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", new PessoaModel());
        return modelAndView;
    }
    //SALVAR (SALVA E CARREGA O LISTAR NA MESMA PÁGINA)
    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa") //** ignora o que vem antes na url
    public ModelAndView salvar(PessoaModel pessoaModel) {
        pessoaRepository.save(pessoaModel);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIt);
        andView.addObject("pessoaobj", new PessoaModel()); //passando objeto vazio pois está retornnado pra mesma tela

        return andView;
    }
    //LISTAR
    @RequestMapping(method = RequestMethod.GET, value = "/listarpessoas")
    public ModelAndView pessoas() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIt); //pessoas faz interação com thymeleaf
        andView.addObject("pessoaobj", new PessoaModel()); //passando objeto vazio pois está retornnado pra mesma tela

        return andView;
    }
    //EDITAR
    @GetMapping("/editarpessoa/{idpessoa}") //GetMapping subistitui o @RequestMapping acima
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {//idpessoas faz interação com thymeleaf

        Optional<PessoaModel> pessoaModel = pessoaRepository.findById(idpessoa); //instanciando o objeto pessoa

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa"); //retornando pra tela de cadastro
        modelAndView.addObject("pessoaobj", pessoaModel.get()); //retornando pra tela de cadastro
        return modelAndView;

    }
    //EXCLUIR
    @GetMapping("/removerpessoa/{idpessoa}")
    public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {

        pessoaRepository.deleteById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new PessoaModel()); //passando objeto vazio

        return modelAndView;
    }

    @PostMapping("**/pesquisarpessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findPessoaModelByName(nomepesquisa));
        modelAndView.addObject("pessoaobj", new PessoaModel());
        return modelAndView;
    }


}
