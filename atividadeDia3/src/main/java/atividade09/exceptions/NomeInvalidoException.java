package atividade09.exceptions;

public class NomeInvalidoException extends Exception {

    private final String nomeInformado;

    public NomeInvalidoException(String nome) {
        super(String.format(
            "Nome inválido: '%s'. O nome deve ter no mínimo 3 caracteres.", nome
        ));
        this.nomeInformado = nome;
    }

    public String getNomeInformado() { return nomeInformado; }
}
