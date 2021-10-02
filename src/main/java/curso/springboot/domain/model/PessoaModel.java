package curso.springboot.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity //faz o hibernate identificar e criar tabela no banco
public class PessoaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //gera chave automática no banco
    private Long id;
    private String nome;
    private String sobrenome;
    private int idade;
    //CRIANDO RELACIONAMENTO 1 PARA N
    @OneToMany(mappedBy = "pessoaModel")
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
}
