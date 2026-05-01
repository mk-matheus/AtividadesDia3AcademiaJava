package atividade09.model;

import atividade09.exceptions.NomeInvalidoException;
import atividade09.exceptions.NotaInvalidaException;

public class Aluno {

    private static final int    TOTAL_NOTAS   = 3;
    private static final double NOTA_MIN      = 0.0;
    private static final double NOTA_MAX      = 100.0;
    private static final int    NOME_MIN_CHARS = 3;

    private final String   nome;
    private final double[] notas;
    private final double   media;
    private final Situacao situacao;

    public Aluno(String nome, double[] notas)
            throws NomeInvalidoException, NotaInvalidaException {

        validarNome(nome);
        validarNotas(notas);

        this.nome     = nome.trim();
        this.notas    = notas.clone();
        this.media    = calcularMedia(notas);
        this.situacao = Situacao.calcular(media);
    }

    // ── Validações

    private static void validarNome(String nome) throws NomeInvalidoException {
        boolean invalido = nome == null || nome.trim().length() < NOME_MIN_CHARS;
        @SuppressWarnings("unused")
        Void v = invalido ? throwNome(nome) : noop();
    }

    private static void validarNotas(double[] notas) throws NotaInvalidaException {
        for (int i = 0; i < TOTAL_NOTAS; i++) {
            boolean invalida = notas[i] < NOTA_MIN || notas[i] > NOTA_MAX;
            @SuppressWarnings("unused")
            Void v = invalida ? throwNota(notas[i], i + 1) : noop();
        }
    }

    // Helpers que retornam Void para permitir uso em ternário
    private static Void throwNome(String nome) throws NomeInvalidoException {
        throw new NomeInvalidoException(nome == null ? "" : nome.trim());
    }

    private static Void throwNota(double nota, int indice) throws NotaInvalidaException {
        throw new NotaInvalidaException(nota, indice);
    }

    private static Void noop() { return null; }

    // ── Cálculo

    private static double calcularMedia(double[] notas) {
        double soma = 0;
        for (double n : notas) soma += n;
        return soma / TOTAL_NOTAS;
    }

    // ── Getters

    public String   getNome()     { return nome; }
    public double[] getNotas()    { return notas.clone(); }
    public double   getMedia()    { return media; }
    public Situacao getSituacao() { return situacao; }

    public String toRelatorioLinha() {
        return String.format("%-20s | Notas: %5.1f, %5.1f, %5.1f | Média: %5.2f | %s %s",
            nome, notas[0], notas[1], notas[2], media,
            situacao.getIcone(), situacao.getLabel());
    }
}
