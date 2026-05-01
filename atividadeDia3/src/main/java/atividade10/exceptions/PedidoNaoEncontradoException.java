package atividade10.exceptions;

public class PedidoNaoEncontradoException extends Exception {
    public PedidoNaoEncontradoException(String pedidoId) {
        super("Pedido não encontrado: ID '" + pedidoId + "'.");
    }
}
