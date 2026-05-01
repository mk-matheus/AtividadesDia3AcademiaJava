package atividade09.exceptions;

public class NotaInvalidaException extends Exception {

    private final double notaInformada;
    private final int    indiceNota;

    public NotaInvalidaException(double nota, int indice) {
        super(String.format(
            "Nota %d inválida: %.1f. O valor deve estar entre 0 e 100.", indice, nota
        ));
        this.notaInformada = nota;
        this.indiceNota    = indice;
    }

    public double getNotaInformada() { return notaInformada; }
    public int    getIndiceNota()    { return indiceNota; }
}
