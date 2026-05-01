package atividade10.exceptions;

public class EstoqueInsuficienteException extends Exception {
    public EstoqueInsuficienteException(String sku, int solicitado, int disponivel) {
        super(String.format(
            "Estoque insuficiente para '%s'. Solicitado: %d | Disponível: %d.",
            sku, solicitado, disponivel
        ));
    }
}
