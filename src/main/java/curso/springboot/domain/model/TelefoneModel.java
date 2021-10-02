package curso.springboot.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import curso.springboot.domain.model.PessoaModel;
import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Entity
public class TelefoneModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numero;
    private String tipo;

    //CRIANDO RELACIONAMENTO 1 PARA N
    @ForeignKey(name="pessoa_id")
    @ManyToOne //muitos teledones pra uma pessoa
    private PessoaModel pessoaModel;
    //--CRIANDO RELACIONAMENTO 1 PARA N
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public PessoaModel getPessoa() {
        return pessoaModel;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoaModel = pessoa;
    }

}