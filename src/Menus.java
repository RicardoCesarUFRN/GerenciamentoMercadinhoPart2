import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {
	public static Scanner scan = new Scanner(System.in);
	//Coleções
	public static ArrayList<Produto> produtos = new ArrayList<>();
	public static HashMap<String, Produto> produtosMap = new HashMap<>();

	public static void menu() {
		int option = 0;
		do {
			System.out.println("Escolha uma das opções abaixo:");
			System.out.println("1 - Cadastrar produto");
			System.out.println("2 - Adicionar produto");
			System.out.println("3 - Remover produto");
			System.out.println("4 - Listar produto(s)");
			System.out.println("5 - Alterar preço");
			System.out.println("6 - Pesquisar produto pelo nome");
			System.out.println("7 - Sair");

			option = scan.nextInt();

			if (option == 1) {
				cadastrarProdutos();
				salvarDados();
			} else if(option == 2) {
				adicionarProdutos();
				salvarDados();
			} else if (option == 3) {
				removerProdutos();
				salvarDados();
				
			} else if (option == 4) {
				listarProdutos();
				
			} else if (option == 5) {
				alterarPreco();
				salvarDados();	
			} 
			else if (option == 6) {
				pesquisarNomeProduto();
			}
			else if (option == 7) {
				System.out.println("Fechando o programa.\n");
				System.exit(0);
			} 
			
			else
				System.out.println("Opção inválida, digite novamente.\n");
			
		} while (option != 7);
	}
	public static void cadastrarProdutos() {
		try {
			System.out.println("Nome do produto: ");
			String nome = scan.next();
			System.out.println("Preço do produto: ");
			double preco = scan.nextDouble();
			System.out.println("Quantidade do produto sendo estocado: ");
			int qteEstoque = scan.nextInt();
			Produto produto = new Produto(nome, preco, qteEstoque);
			produtos.add(produto);
			//adiciona um par chave-valor
			produtosMap.put(produto.getNome(), produto);
			System.out.println(produto.getNome() + " cadastrado!\n");
		} catch (InputMismatchException e) {
			System.out.println("Input incompatível");
		}
	}
	public static void adicionarProdutos() {
		System.out.println("Qual o ID do produto que você adicionará ao estoque?");
		int option = scan.nextInt();
		Produto p1;
		p1 = produtos.get(option);
		System.out.println(p1.getNome() + " escolhido");
		System.out.println("Quantas unidades de " + p1.getNome() +" você deseja adicionar ao estoque?");
		int opc2 = scan.nextInt();
		int resultado = opc2 + p1.getQteEstoque();
		p1.setQteEstoque(resultado);
		System.out.println(opc2 + " unidades de " + p1.getNome() + " foram adicionadas ao estoque, estoque total agora é: " + p1.getQteEstoque());
	}
	public static void removerProdutos() {
		
		System.out.println("Qual o ID do produto que você removerá do estoque?");
		int option = scan.nextInt();
		Produto p1;
		p1 = produtos.get(option);
		System.out.println(p1.getNome() + " escolhido");
		System.out.println("Quantas unidades de " + p1.getNome() +" você deseja remover do estoque?");
		int opc2 = scan.nextInt();
		if(opc2 > p1.getQteEstoque()) {
			System.out.println("Quantidade de itens removidos maior que a quantidade em estoque, escolha novamente.");
			removerProdutos();
		}
		else {
			int resultado = p1.getQteEstoque() - opc2;
			p1.setQteEstoque(resultado);
			System.out.println(opc2 + " unidades de " + p1.getNome() + " removidas, restaram " + p1.getQteEstoque() + " em estoque.");
			produtos.set(option,p1);
		}
	}
	public static void listarProdutos() {
		if(produtos.size() > 0) {
			System.out.println("Lista de produtos: ");
			
			for (Produto p : produtos) {
				System.out.println(p);
			}
			System.out.println("");
		}
		else {
			System.out.println("Estoque zerado");
		}
	}
	
	public static void alterarPreco() {
		//Tratamento de exceção
		try {
			System.out.println("Qual a id do produto que você deseja mudar o preço?");
			int option = scan.nextInt();
			Produto p1;
			p1 = produtos.get(option);
			System.out.println(p1.getNome() + " escolhido(a)");
			System.out.println("Qual o novo preço do(a) " + p1.getNome() + "?");
			double opc2 = scan.nextDouble();
			p1.setPreco(opc2);
			produtos.set(option, p1);
			System.out.println("Preço do(a) "+ p1.getNome() + " alterado com sucesso.");
		} catch (InputMismatchException e) {
			System.out.println("Input Incompatível");
		};
	}
	
	public static void pesquisarNomeProduto() {
		System.out.println("Digite o nome do produto que você quer conferir se há no estoque: ");
		String nome = scan.next();
	
		boolean nomePresente;
		// verifica se a chave está presente
		nomePresente = produtosMap.containsKey(nome);
		
		if (nomePresente == true) {
			System.out.println("Produto encontrado no estoque!");
		}
		else {
			System.out.println("Produto não encontrado!");
		}
	}
	
	public static void salvarDados() {
        try {
            FileWriter fileWriter = new FileWriter("produtos.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Produto produto : produtos) {
                bufferedWriter.write(produto.getNome() + ";" + produto.getPreco() + ";" + produto.getQteEstoque());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Dados salvos com sucesso!\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public static void carregarDados() {
        try {
            FileReader fileReader = new FileReader("produtos.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                String[] dados = line.split(";");
                String nome = dados[0];
                double preco = Double.parseDouble(dados[1]);
                int qteEstoque = Integer.parseInt(dados[2]);

                Produto produto = new Produto(nome, preco, qteEstoque);
                produtos.add(produto);
                produtosMap.put(produto.getNome(),produto);
            }

            bufferedReader.close();
            fileReader.close();

            System.out.println("Dados carregados com sucesso!\n");
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }
}