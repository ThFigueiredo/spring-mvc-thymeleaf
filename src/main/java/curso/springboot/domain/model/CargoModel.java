package curso.springboot.domain.model;

public enum CargoModel {

	JUNIOR("J�nior"),
	PLENO("Pleno"),
	SENIOR("S�nio");

	private String nome;
	private String valor;

	private CargoModel(String nome) {
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor = this.name();
	}
	

}
