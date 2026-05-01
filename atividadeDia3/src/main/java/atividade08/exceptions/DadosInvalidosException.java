package atividade08.exceptions;

public class DadosInvalidosException extends Exception {

    private final String campo;

    public DadosInvalidosException(String campo, String motivo) {
        super(String.format("Campo '%s' inválido: %s", campo, motivo));
        this.campo = campo;
    }

    public String getCampo() { return campo; }
}
