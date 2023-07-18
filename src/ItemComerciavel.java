//Classe abstrata
public abstract class ItemComerciavel {
	private String nome;
	private double preco;

	public ItemComerciavel(String nome, double preco) {
		this.preco = preco;
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
