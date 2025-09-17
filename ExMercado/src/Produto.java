public class Produto {
    private Descricao descricao;
    private Double preco;
    private int qtdEstoque;

    public Produto(Descricao descricao, Double preco, int qtdEstoque) {
        this.descricao = descricao;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
    }

    public Descricao getDescricao() {
        return this.descricao;
    }

    public double getPreco() {
        return this.preco;
    }

    public int getQtdEstoque() {
        return this.qtdEstoque;
    }

    public void atualizarEstoque(int qtdPedido){
        this.qtdEstoque -= qtdPedido;
    }
}
