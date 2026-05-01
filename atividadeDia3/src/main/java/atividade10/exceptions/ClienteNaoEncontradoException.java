package atividade10.exceptions;

public class ClienteNaoEncontradoException extends Exception {
    public ClienteNaoEncontradoException(String clienteId) {
        super("Cliente não encontrado: ID '" + clienteId + "'.");
    }
}
