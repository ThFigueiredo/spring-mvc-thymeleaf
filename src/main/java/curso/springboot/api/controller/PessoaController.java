package curso.springboot.api.controller;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.model.TelefoneModel;
import curso.springboot.domain.repository.PessoaRepository;
import curso.springboot.domain.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired //anotação autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    //SALVAR (MÉTODO DE REDIRECIONAMENTO)
    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio() { //início = nome do método
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", new PessoaModel());


        Iterable<PessoaModel> pessoaIt = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoaIt); //pessoas faz interação com thymeleaf
        return modelAndView;
    }
    //SALVAR (SALVA E CARREGA O LISTAR NA MESMA PÁGINA)
    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa") //** ignora o que vem antes na url
    public ModelAndView salvar(@Valid PessoaModel pessoaModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            Iterable<PessoaModel> pessoasIt = pessoaRepository.findAll();
            modelAndView.addObject("pessoas", pessoasIt);
            modelAndView.addObject("pessoaobj", pessoaModel);

            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage()); // vem das anotações @NotEmpty e outras
            }

            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

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
    //EXCLUIR TELEFONE
    @GetMapping("/removertelefone/{idtelefone}")
    public ModelAndView removertelefone(@PathVariable("idtelefone") Long idtelefone) {

        PessoaModel pessoaModel = telefoneRepository.findById(idtelefone).get().getPessoa(); //carregando e retornando o objeto telefone

        telefoneRepository.deleteById(idtelefone); //deletando um telefone específico

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones"); //retornando pra mesma tela
        modelAndView.addObject("pessoaobj", pessoaModel); //passando o objeto pai pra mostrar na tela
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaModel.getId())); //carrega novamente - o que foi removido

        return modelAndView;
    }
    //FAZENDO A PESQUISA POR NOME
    @PostMapping("**/pesquisarpessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findPessoaModelByName(nomepesquisa));
        modelAndView.addObject("pessoaobj", new PessoaModel());
        return modelAndView;
    }
    //LISTAR TELEFONE
    @GetMapping("/telefones/{idpessoa}")
    public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {

        Optional<PessoaModel> pessoa = pessoaRepository.findById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa.get());
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa)); //lista os telefones ao entrar na pagina
        return modelAndView;

    }
    //CADASTRO TELEFONES PARA UMA PESSOA
    @PostMapping("**/addfonePessoa/{pessoaid}")
    public ModelAndView addFonePessoa(TelefoneModel telefoneModel,
                                      @PathVariable("pessoaid") Long pessoaid) {

        PessoaModel pessoa = pessoaRepository.findById(pessoaid).get();
        telefoneModel.setPessoa(pessoa);

        telefoneRepository.save(telefoneModel);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid)); //telefones -> conexao com o thymeleaf
        return modelAndView;
    }



}
