package atividade10.model;

public class ItemPedido {

    private final Produto produto;
    private final int     quantidade;
    private final double  precoUnitario;

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.produto       = produto;
        this.quantidade    = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public double getSubtotal() { return precoUnitario * quantidade; }

    public Produto getProduto()        { return produto; }
    public int     getQuantidade()     { return quantidade; }
    public double  getPrecoUnitario()  { return precoUnitario; }

    @Override
    public String toString() {
        return String.format("  %-10s %-20s x%d  @ R$ %.2f = R$ %.2f",
            produto.getSku(), produto.getNome(), quantidade, precoUnitario, getSubtotal());
    }
}
