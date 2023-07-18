import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenusGUI {

    public static void criarGUI() {
        JFrame frame = new JFrame("Menu Mercadinho");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        JButton botaoCadastrar = new JButton("Cadastrar produto");
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	cadastrarProdutoGUI();
            }
        });
        panel.add(botaoCadastrar);

        JButton botaoAdicionar = new JButton("Adicionar produto");
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	adicionarProdutoGUI();
            }
        });
        panel.add(botaoAdicionar);

        JButton botaoRemover = new JButton("Remover produto");
        botaoRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	removerProdutoGUI();
            }
        });
        panel.add(botaoRemover);

        JButton botaoListar = new JButton("Listar produto(s)");
        botaoListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	listarProdutosGUI();
            }
        });
        panel.add(botaoListar);

        JButton botaoAlterarPreco = new JButton("Alterar preço");
        botaoAlterarPreco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	alterarPrecoGUI();
            }
        });
        panel.add(botaoAlterarPreco);

        JButton botaoPesquisar = new JButton("Procurar produto");
        botaoPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	procurarProdutoGUI();
            }
        });
        panel.add(botaoPesquisar);

        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(botaoSair);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    public static void cadastrarProdutoGUI() {
        JTextField nomeT = new JTextField();
        JTextField precoT = new JTextField();
        JTextField qtdEstoqueT = new JTextField();

        Object[] mensagem = {"Nome:", nomeT,"Preço:", precoT,"Quantidade em estoque:", qtdEstoqueT};

        int option = JOptionPane.showConfirmDialog(null, mensagem, "Cadastrar produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeT.getText();
                double preco = Double.parseDouble(precoT.getText());
                int qtdEstoque = Integer.parseInt(qtdEstoqueT.getText());

                Produto produto = new Produto(nome, preco, qtdEstoque);
                Menus.produtos.add(produto);
                Menus.produtosMap.put(produto.getNome(), produto);
                Menus.salvarDados();

                JOptionPane.showMessageDialog(null, "Produto cadastrado: " + produto.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Valores inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void adicionarProdutoGUI() {
        JTextField idT = new JTextField();
        JTextField qteT = new JTextField();

        Object[] mensagem = {"ID do produto:", idT, "Quantidade:", qteT};

        int option = JOptionPane.showConfirmDialog(null, mensagem, "Adicionar produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idT.getText());
                int quantidade = Integer.parseInt(qteT.getText());

                adicionarProdutoAoEstoque(id, quantidade);
            } 
            catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(null, "Valores inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void adicionarProdutoAoEstoque(int id, int quantidade) {
        if (id >= 0 && id < Menus.produtos.size()) {
            Produto produto = Menus.produtos.get(id);

            int novaQte = produto.getQteEstoque() + quantidade;
            produto.setQteEstoque(novaQte);
            Menus.salvarDados();

            JOptionPane.showMessageDialog(null, quantidade + " unidades do produto " + produto.getNome() + " foram adicionadas ao estoque. Estoque total agora é: " + novaQte, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } 
        else {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void removerProdutoGUI() {
        JTextField idT = new JTextField();
        JTextField qteT = new JTextField();

        Object[] message = {"ID do produto:", idT, "Quantidade:", qteT};

        int option = JOptionPane.showConfirmDialog(null, message, "Remover produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idT.getText());
                int quantidade = Integer.parseInt(qteT.getText());
                removerProdutoDoEstoque(id, quantidade);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Valores inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void removerProdutoDoEstoque(int id, int quantidade) {
        if (id >= 0 && id < Menus.produtos.size()) {
            Produto produto = Menus.produtos.get(id);

            if (quantidade <= produto.getQteEstoque()) {
                int novaQte = produto.getQteEstoque() - quantidade;
                produto.setQteEstoque(novaQte);
                Menus.salvarDados();

                JOptionPane.showMessageDialog(null, quantidade + " unidades do produto " + produto.getNome() + " foram removidas do estoque. Estoque total agora é: " + novaQte, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } 
            else {
                JOptionPane.showMessageDialog(null, "Quantidade de itens a remover é maior que a quantidade em estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void listarProdutosGUI() {
        StringBuilder list = new StringBuilder();
        if (Menus.produtos.size() > 0) {
            list.append("Lista de produtos:\n\n");
            for (Produto produto : Menus.produtos) {
                list.append(produto.toString());
                list.append("\n");
            }
        } 
        else {
            list.append("Estoque zerado");
        }

        JTextArea textArea = new JTextArea(list.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Produtos", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void alterarPrecoGUI() {
        JTextField idT = new JTextField();
        JTextField precoT = new JTextField();

        Object[] mensagem = {"ID do produto:", idT, "Novo preço:", precoT};

        int option = JOptionPane.showConfirmDialog(null, mensagem, "Alterar preço", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idT.getText());
                double preco = Double.parseDouble(precoT.getText());
                alterarPrecoDoProduto(id, preco);
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Valores inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void alterarPrecoDoProduto(int id, double preco) {
        if (id >= 0 && id < Menus.produtos.size()) {
            Produto produto = Menus.produtos.get(id);

            produto.setPreco(preco);
            Menus.salvarDados();

            JOptionPane.showMessageDialog(null, "O preço do produto " + produto.getNome() + " foi alterado com sucesso para " + preco + " R$.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } 
        else {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void procurarProdutoGUI() {
        JTextField nomeT = new JTextField();

        Object[] mensagem = {"Nome do produto:", nomeT};
        int option = JOptionPane.showConfirmDialog(null, mensagem, "Pesquisar produto pelo nome", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String nomeProduto = nomeT.getText();
            pesquisarNome(nomeProduto);
        }
    }

    public static void pesquisarNome(String nomeProduto) {
        boolean nomePresente = Menus.produtosMap.containsKey(nomeProduto);

        if (nomePresente) {
            JOptionPane.showMessageDialog(null, "Produto encontrado no estoque!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
