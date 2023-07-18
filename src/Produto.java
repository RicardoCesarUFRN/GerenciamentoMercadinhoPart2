//Herança, classes e objetos
public class Produto extends ItemComerciavel implements Mercadoria{
	private int qteEstoque;
	protected int id;
	protected static int numeroProduto = 0;
	
	public Produto(String nome, double preco, int qteEstoque){
		super(nome, preco);
		this.qteEstoque = qteEstoque;
		this.id = numeroProduto;
		numeroProduto++;
	}

	public int getQteEstoque() {
		return qteEstoque;
	}

	public void setQteEstoque(int qteEstoque) {
		this.qteEstoque = qteEstoque;
	}

	public int getId() {
		return id;
	}
	public String toString() {
		return "ID: " + this.getId() +  "\nNome: " + this.getNome() + "\nPreço: " + this.getPreco() + " Reais" + "\nQuantidade em estoque: " + this.getQteEstoque() + " unidades";
		
	}

	@Override
	public void comprarMercadoria() {
		System.out.println("Estoque baixo, precisa comprar mercadoria!");
		
	}



}
