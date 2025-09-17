public class Item{
    Produto produto;
    int qtd;

    public Item(Produto produto, int qtd){
        if(produto.getQtdEstoque() < qtd){
            System.out.println("Estoque insuficiente, Estoque atual de " + produto.getDescricao() + ": " + produto.getQtdEstoque());
        } else{
        this.produto = produto;
        this.qtd = qtd;
        }
    }
}

//public static void main(String[] args) {
//    Produto p = new Produto(Descricao.FEIJAO, 25.39, 25);
//    Item i = new Item(p,3);
//    System.out.println(i.produto.descricao);
//    System.out.println(i.produto.preco);
//    System.out.println(i.qtd);
//    Item i2 = new Item(p, 26);
//    System.out.println(i2.produto.descricao);
//}
