package atividade08.exceptions;

public class ValorInvalidoException extends Exception {

    private final double valorInformado;

    public ValorInvalidoException(double valorInformado) {
        super(String.format(
            "Valor inválido informado: R$ %.2f. O valor deve ser maior que zero.",
            valorInformado
        ));
        this.valorInformado = valorInformado;
    }

    public ValorInvalidoException(String mensagem) {
        super(mensagem);
        this.valorInformado = 0;
    }

    public double getValorInformado() { return valorInformado; }
}
