import java.util.ArrayList;

public class Pedido {
    TipoPagamento pagamento;
    ArrayList<Item> itens ;
    Cliente cliente;

    public Pedido(){
        this.itens = new ArrayList<Item>();
    }


    public void novoItem(Item i){
        if (i.produto != null) {
            this.itens.add(i);
            i.produto.atualizarEstoque(i.qtd);
        }
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setTipoPagamento(TipoPagamento pagamento){
        this.pagamento = pagamento;
    }
}

//public static void main(String[] args) {
//    Pedido pedido = new Pedido(TipoPagamento.PIX);
//    System.out.println(pedido.pagamento);
//    Produto p = new Produto(Descricao.FEIJAO, 25.39, 25);
//    Item i = new Item(p, 23);
//    System.out.println(i.produto == null);
//    Item i2 = pedido.newItem(i);
//    System.out.println("Quantidade: " + i2.qtd + ", Descricao: " + i2.produto.descricao + ", Preco: " + i2.produto.preco);
//}