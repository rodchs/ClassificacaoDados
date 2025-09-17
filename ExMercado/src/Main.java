import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    public static Pedido novoPedido(Scanner input, ArrayList<Produto> produtos){
        System.out.println("Informe nome do cliente: ");
        String nome = input.nextLine();
        System.out.println("Informe CPF do cliente: ");
        String cpf = input.nextLine();
        Pedido p = new Pedido();
        Cliente c = new Cliente(nome, cpf);
        p.setCliente(c);

        while(true){
        System.out.println("-------- PRECOS E ESTOQUE ----------");
        for (Produto produto : produtos) {
            System.out.println(produto.getDescricao() + ": " + produto.getPreco() + ", Estoque: " + produto.getQtdEstoque());
        }

            System.out.println("Escolha o produto desejado: 0 para ARROZ, 1 para FEIJAO,2 para FARINHA, 3 para LEITE, 4 para finalizar o pedido");
            int produtoIndex = input.nextInt();
            input.nextLine();

            if (produtoIndex == 4) {
                return p;
            }

            System.out.println("Informe quantidade desejada: ");
            int quantidade = input.nextInt();
            input.nextLine();

            Item i = new Item(produtos.get(produtoIndex), quantidade);
            p.novoItem(i);
        }

    }

    public static void realizarPagamento(Scanner input, Pedido p){
        System.out.println("--------INFORMACOES DO PEDIDO---------");
        System.out.println("Nome do cliente: " + p.cliente.getNome() + "\n" + "CPF do cliente: " + p.cliente.getCpf());
        double total = 0;
        for(Item i : p.itens){
            System.out.println(i.produto.getDescricao() + ": " + i.qtd + "x");
            total += (i.qtd * i.produto.getPreco()) ;
        }
        System.out.println("Total do pedido: R$" + total);
        System.out.println("Informar metodo de pagamento: DINHEIRO, DEBITO, CREDITO, PIX ou CHEQUE");
        String tipo = input.nextLine();
        if(tipo.equalsIgnoreCase("DINHEIRO")){
        p.setTipoPagamento(TipoPagamento.DINHEIRO);
        } else if (tipo.equalsIgnoreCase("DEBITO")) {
            p.setTipoPagamento(TipoPagamento.DEBITO);
        } else if (tipo.equalsIgnoreCase("CREDITO")) {
            p.setTipoPagamento(TipoPagamento.CREDITO);
        } else if (tipo.equalsIgnoreCase("PIX")) {
            p.setTipoPagamento(TipoPagamento.PIX);
        }  else if (tipo.equalsIgnoreCase("CHEQUE")) {
            p.setTipoPagamento(TipoPagamento.CHEQUE);
        }
        if(p.pagamento != null) {
            System.out.println("Pagamento finalizado.");
        }
    }

    public static void main(String[] args) {

        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

        Produto ar = new Produto(Descricao.ARROZ, 10.99, 21);
        Produto fe = new Produto(Descricao.FEIJAO, 6.99, 39);
        Produto fa = new Produto(Descricao.FARINHA, 12.99, 32);
        Produto le = new Produto(Descricao.LEITE, 4.99, 15);

        listaProdutos.add(ar);
        listaProdutos.add(fe);
        listaProdutos.add(fa);
        listaProdutos.add(le);

        Scanner input = new Scanner(System.in);
        int c = 0;

        Pedido p = new Pedido();

        while(c == 0){
            System.out.println("------Menu--------");
            System.out.println("Opcao 1: Novo pedido");
            System.out.println("Opcao 2: Realizar pagamento");
            System.out.println("Opcao 3: Sair");
            int op = input.nextInt();
            input.nextLine();
            Cliente c1 = new Cliente("a", "b");
            if(op == 1){
                p = novoPedido(input, listaProdutos);
            }
            else if(op == 2){
                realizarPagamento(input, p);
            }
            else if(op == 3){
                c = 1;
            }

        }
    }
}