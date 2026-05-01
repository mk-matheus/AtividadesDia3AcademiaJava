package atividade08.exceptions;

public class ContaInativaException extends Exception {

    private final int numeroConta;

    public ContaInativaException(int numeroConta) {
        super(String.format(
            "A conta %d está inativa e não pode realizar operações.", numeroConta
        ));
        this.numeroConta = numeroConta;
    }

    public int getNumeroConta() { return numeroConta; }
}
