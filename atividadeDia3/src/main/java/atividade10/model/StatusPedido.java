package atividade10.model;

/** Ciclo de vida de um pedido no sistema. */
public enum StatusPedido {
    ABERTO,
    RESERVED,
    PAID,
    FAILED,
    CANCELLED;

    /** Verifica se a transição de status é válida. */
    public boolean podeTransitarPara(StatusPedido destino) {
        return switch (this) {
            case ABERTO    -> destino == RESERVED || destino == CANCELLED;
            case RESERVED  -> destino == PAID || destino == FAILED || destino == CANCELLED;
            case PAID, FAILED, CANCELLED -> false; // estados finais
        };
    }
}
