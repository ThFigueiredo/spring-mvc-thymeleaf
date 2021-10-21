package curso.springboot.api.controller;

import curso.springboot.domain.model.PessoaModel;
import curso.springboot.domain.model.TelefoneModel;
import curso.springboot.domain.repository.PessoaRepository;
import curso.springboot.domain.repository.ProfissaoRepository;
import curso.springboot.domain.repository.TelefoneRepository;
import curso.springboot.domain.service.PessoaService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {
    //void -> é quando nao tem retorno
    @Autowired //anotação autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ProfissaoRepository profissaoRepository;

    //SALVAR (MÉTODO DE REDIRECIONAMENTO) (carrega os dados para a salvar. Após clicar no submit do form, será direcionado o método salvar)
    @GetMapping("**/cadpessoa")
    public ModelAndView index2() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", new PessoaModel());
        modelAndView.addObject("profissoes", profissaoRepository.findAll());
        return modelAndView;
    }

    //SALVAR
    @PostMapping("**/salvarpessoa") //ignora o que vem antes -> serve para a ediçao
    public ModelAndView salvar(@Valid PessoaModel pessoaModel,
                               BindingResult bindingResult, final MultipartFile file, Pageable pageable) throws IOException, NotFoundException {

        pessoaModel.setTelefones(telefoneRepository.getTelefones(pessoaModel.getId()));
//        System.out.println(file.getContentType()); //pegando o tipo do arquivo
//        System.out.println(file.getName());
//        System.out.println(file.getOriginalFilename());
        //VALIDAÇÃO
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/listar"); //retonando para a tela cadastro pessoa
            Iterable<PessoaModel> pessoasIt = pessoaService.findAll(pageable);
            modelAndView.addObject("pessoas", pessoasIt);
            modelAndView.addObject("pessoaobj", pessoaModel); //retornando o objeto pessoa

            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage()); // vem das anotações @NotEmpty e outras
            }

            modelAndView.addObject("msg", msg);
            modelAndView.addObject("profissoes", profissaoRepository.findAll());
            return modelAndView;
        }

        if (file.getSize() > 0) { /*Cadastrando um curriculo*/
            pessoaModel.setCurriculo(file.getBytes());
            pessoaModel.setTipoFileCurriculo(file.getContentType());
            pessoaModel.setNomeFileCurriculo(file.getOriginalFilename());
        }else {
            if (pessoaModel.getId() != null && pessoaModel.getId() > 0) {// editando

                PessoaModel pessoalTemp = pessoaRepository.
                        findById(pessoaModel.getId()).get();

                pessoaModel.setCurriculo(pessoalTemp.getCurriculo());
                pessoaModel.setTipoFileCurriculo(pessoalTemp.getTipoFileCurriculo());
                pessoaModel.setNomeFileCurriculo(pessoalTemp.getNomeFileCurriculo());
            }
        }
        //--VALIDAÇÃO

        pessoaService.create(pessoaModel);
        ModelAndView modelAndView = new ModelAndView("redirect:/listapessoas");
        return modelAndView;
    }

    //LISTAR
    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView pessoas(Pageable pageable) {
        ModelAndView andView = new ModelAndView("cadastro/listar");
        Iterable<PessoaModel> pessoaIt = pessoaService.findAll(pageable);
        andView.addObject("pessoas", pessoaIt);
        andView.addObject("pessoaobj", new PessoaModel()); //passando objeto vazio
        return andView;
    }
    //EDITAR (carrega os dados para a edição. Após clicar no submit do form, será direcionado o método salvar)
    @GetMapping("/editarpessoa/{idpessoa}") //GetMapping subistitui o @RequestMapping
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) throws NotFoundException {//idpessoas faz interação com thymeleaf
        Optional<PessoaModel> pessoaModel = Optional.ofNullable(pessoaService.findById(idpessoa)); //instanciando o objeto pessoa
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", pessoaModel.get());
        modelAndView.addObject("profissoes", profissaoRepository.findAll());
        return modelAndView;
    }

    //EXCLUIR
    @GetMapping("/removerpessoa/{idpessoa}")
    public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) throws NotFoundException {
        pessoaService.delete(idpessoa);
        ModelAndView modelAndView = new ModelAndView("redirect:/listapessoas");
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
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa,
                                  @RequestParam("pesqsexo") String pesqsexo) {

        List<PessoaModel> pessoas = new ArrayList<PessoaModel>();

        if (pesqsexo != null && !pesqsexo.isEmpty()) {
            pessoas = pessoaRepository.findPessoaModelByNameSexo(nomepesquisa, pesqsexo);
        } else {
            pessoas = pessoaRepository.findPessoaModelByName(nomepesquisa);
        }

        ModelAndView modelAndView = new ModelAndView("cadastro/listar");
        modelAndView.addObject("pessoas", pessoas);
        modelAndView.addObject("pessoaobj", new PessoaModel());
        return modelAndView;
    }

    //PDF
    @GetMapping("**/pesquisarpessoa")
    public void imprimePdf(@RequestParam("nomepesquisa") String nomepesquisa,
                           @RequestParam("pesqsexo") String pesqsexo,
                           HttpServletRequest request,
                           HttpServletResponse response, Pageable pageable) throws Exception {

        List<PessoaModel> pessoas = new ArrayList<PessoaModel>();

        if (pesqsexo != null && !pesqsexo.isEmpty()
                && nomepesquisa != null && !nomepesquisa.isEmpty()) {/*Busca por nome e sexo*/

            pessoas = pessoaRepository.findPessoaModelByNameSexo(nomepesquisa, pesqsexo);

        } else if (nomepesquisa != null && !nomepesquisa.isEmpty()) {/*Busca somente por nome*/

            pessoas = pessoaRepository.findPessoaModelByName(nomepesquisa);

        } else if (pesqsexo != null && !pesqsexo.isEmpty()) {/*Busca somente por sexo*/

            pessoas = pessoaRepository.findPessoaBySexo(pesqsexo);

        } else {/*Busca todos*/
            Iterable<PessoaModel> iterator = pessoaService.findAll(pageable);
            for (PessoaModel pessoa : iterator) {
                pessoas.add(pessoa);
            }
        }

        /*Chame o serviço que faz a geração do relatorio*/
        byte[] pdf = ReportUtilController.gerarRelatorio(pessoas, "pessoa", request.getServletContext());

        /*Tamanho da resposta*/
        response.setContentLength(pdf.length);

        /*Definir na resposta o tipo de arquivo*/
        response.setContentType("application/octet-stream");

        /*Definir o cabeçalho da resposta*/
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
        response.setHeader(headerKey, headerValue);

        /*Finaliza a resposta pro navegador*/
        response.getOutputStream().write(pdf);

    }

    //LISTAR TELEFONE
    @GetMapping("/telefones/{idpessoa}")
    public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) throws NotFoundException {

        Optional<PessoaModel> pessoa = Optional.ofNullable(pessoaService.findById(idpessoa));

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

    @GetMapping("**/baixarcurriculo/{idpessoa}")
    public void baixarcurriculo(@PathVariable("idpessoa") Long idpessoa,
                                HttpServletResponse response) throws Exception {


        /*Chame o serviço que faz a geração do relatorio*/
        byte[] pdf = pessoaRepository.findById(idpessoa).get().getCurriculo();

        if (pdf != null) {
            /*Tamanho da resposta*/
            response.setContentLength(pdf.length);

            /*Definir na resposta o tipo de arquivo*/
            response.setContentType("application/octet-stream");

            /*Definir o cabeçalho da resposta*/
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
            response.setHeader(headerKey, headerValue);

            /*Finaliza a resposta pro navegador*/
            response.getOutputStream().write(pdf);
        }

    }


}
