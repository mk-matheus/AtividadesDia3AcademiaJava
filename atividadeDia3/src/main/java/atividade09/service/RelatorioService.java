package atividade09.service;

import atividade09.model.Aluno;
import atividade09.model.Situacao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioService {

    private static final String LINHA  = "─".repeat(72);
    private static final String DLINHA = "═".repeat(72);

    public void exibirRelatorioIndividual(List<Aluno> alunos) {
        System.out.println("\n" + DLINHA);
        System.out.println("  1. RELATÓRIO INDIVIDUAL");
        System.out.println(DLINHA);
        alunos.forEach(a -> System.out.println("  " + a.toRelatorioLinha()));
        System.out.println(DLINHA);
    }

    public void exibirEstatisticas(List<Aluno> alunos) {
        double maiorMedia = alunos.stream().mapToDouble(Aluno::getMedia).max().orElse(0);
        double menorMedia = alunos.stream().mapToDouble(Aluno::getMedia).min().orElse(0);
        double mediaGeral = alunos.stream().mapToDouble(Aluno::getMedia).average().orElse(0);

        System.out.println("\n" + DLINHA);
        System.out.println("  2. ESTATÍSTICAS DA TURMA");
        System.out.println(DLINHA);
        System.out.printf("  %-25s %.2f%n", "Maior média:", maiorMedia);
        System.out.printf("  %-25s %.2f%n", "Menor média:", menorMedia);
        System.out.printf("  %-25s %.2f%n", "Média geral da turma:", mediaGeral);
        System.out.println(DLINHA);
    }

    public void exibirDistribuicao(List<Aluno> alunos) {
        long aprovados   = contarPorSituacao(alunos, Situacao.APROVADO);
        long recuperacao = contarPorSituacao(alunos, Situacao.RECUPERACAO);
        long reprovados  = contarPorSituacao(alunos, Situacao.REPROVADO);
        int  total       = alunos.size();

        System.out.println("\n" + DLINHA);
        System.out.println("  3. DISTRIBUIÇÃO DE RESULTADOS");
        System.out.println(DLINHA);
        System.out.printf("  %-16s %3d aluno(s)  %s%n",
            Situacao.APROVADO.getIcone()    + " APROVADOS:",   aprovados,   barra(aprovados,   total));
        System.out.printf("  %-16s %3d aluno(s)  %s%n",
            Situacao.RECUPERACAO.getIcone() + " RECUPERAÇÃO:", recuperacao, barra(recuperacao, total));
        System.out.printf("  %-16s %3d aluno(s)  %s%n",
            Situacao.REPROVADO.getIcone()   + " REPROVADOS:",  reprovados,  barra(reprovados,  total));
        System.out.println(DLINHA);
    }

    public void exibirMelhores(List<Aluno> alunos) {
        double maiorMedia = alunos.stream().mapToDouble(Aluno::getMedia).max().orElse(0);
        List<Aluno> melhores = alunos.stream()
            .filter(a -> a.getMedia() == maiorMedia)
            .collect(Collectors.toList());

        System.out.println("\n" + DLINHA);
        System.out.printf("  4. MELHOR(ES) ALUNO(S) — Média: %.2f%n", maiorMedia);
        System.out.println(DLINHA);
        melhores.forEach(a -> System.out.println("  🏆 " + a.toRelatorioLinha()));
        System.out.println(DLINHA);
    }

    // ── Utilitários privados

    private long contarPorSituacao(List<Aluno> alunos, Situacao s) {
        return alunos.stream().filter(a -> a.getSituacao() == s).count();
    }

    private String barra(long count, int total) {
        int preenchido = total == 0 ? 0 : (int) Math.round((double) count / total * 20);
        char[] barra   = new char[20];
        Arrays.fill(barra, '░');
        Arrays.fill(barra, 0, preenchido, '█');
        return "[" + new String(barra) + "] " +
               (total == 0 ? "0" : String.format("%.0f%%", (double) count / total * 100));
    }
}
