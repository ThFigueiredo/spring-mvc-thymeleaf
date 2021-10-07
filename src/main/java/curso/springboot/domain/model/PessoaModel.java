package curso.springboot.domain.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
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

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;
    private String ibge;

    private String sexopessoa;

    @ManyToOne //muitas pessoas para uma profissão
    private ProfissaoModel profissaoModel; //criando pbjeto profissão

    @Enumerated(EnumType.STRING)
    private CargoModel cargoModel;

    public CargoModel getCargoModel() {
        return cargoModel;
    }

    public void setCargoModel(CargoModel cargoModel) {
        this.cargoModel = cargoModel;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Lob
    private byte[] curriculo;


    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }


    public ProfissaoModel getProfissaoModel() {
        return profissaoModel;
    }

    public void setProfissaoModel(ProfissaoModel profissaoModel) {
        this.profissaoModel = profissaoModel;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }


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

    public String getSexopessoa() {
        return sexopessoa;
    }

    public void setSexopessoa(String sexopessoa) {
        this.sexopessoa = sexopessoa;
    }
}
