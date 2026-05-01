package atividade10.exceptions;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException(String sku) {
        super("Produto não encontrado: SKU '" + sku + "'.");
    }
}
