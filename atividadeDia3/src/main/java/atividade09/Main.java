package atividade09;

import atividade09.model.Aluno;
import atividade09.service.LeituraService;
import atividade09.service.RelatorioService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("      ATIVIDADE 09 — SISTEMA DE GESTÃO ACADÊMICA                    ");

        LeituraService   leitura   = new LeituraService(scanner);
        RelatorioService relatorio = new RelatorioService();

        List<Aluno> alunos = leitura.lerAlunos();

        @SuppressWarnings("unused")
        Void result = alunos.isEmpty()
            ? printVazio()
            : gerarRelatorios(relatorio, alunos);

        scanner.close();
    }

    private static Void printVazio() {
        System.out.println("\nNenhum aluno cadastrado. Encerrando.");
        return null;
    }

    private static Void gerarRelatorios(RelatorioService service, List<Aluno> alunos) {
        System.out.println("\n\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        RELATÓRIOS DA TURMA                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

        service.exibirRelatorioIndividual(alunos);
        service.exibirEstatisticas(alunos);
        service.exibirDistribuicao(alunos);
        service.exibirMelhores(alunos);

        System.out.println("\n  Total de alunos processados: " + alunos.size());
        return null;
    }
}
