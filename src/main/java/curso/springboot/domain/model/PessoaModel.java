package curso.springboot.domain.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity //faz o hibernate identificar e criar tabela no banco
public class PessoaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotNull(message = "Sobrenome não pode ser nulo")
    @NotEmpty(message = "Sobrenome não pode ser vazio")
    private String sobrenome;

    @Min(value = 18, message = "Idade inválida")
    private int idade;

    public String getQuaquer() {
        return quaquer;
    }

    public void setQuaquer(String quaquer) {
        this.quaquer = quaquer;
    }

    public List<TelefoneModel> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneModel> telefones) {
        this.telefones = telefones;
    }

    private String quaquer;
    //CRIANDO RELACIONAMENTO 1 PARA N
    @OneToMany(mappedBy = "pessoaModel", orphanRemoval = true, cascade = CascadeType.ALL) //orphanRemoval = true, cascade = CascadeType.ALL ->removendo todos os telefones quando uma pessoa for excluida
    private List<TelefoneModel> telefones;
    //--CRIANDO RELACIONAMENTO 1 PARA N
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getIdade() { return idade; }

    public void setIdade(int idade) { this.idade = idade; }

    public void add(PessoaModel pessoa) {
    }
}
