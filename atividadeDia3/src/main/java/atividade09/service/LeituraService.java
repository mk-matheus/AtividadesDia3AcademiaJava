package atividade09.service;

import atividade09.exceptions.NomeInvalidoException;
import atividade09.exceptions.NotaInvalidaException;
import atividade09.model.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeituraService {

    private final Scanner scanner;

    public LeituraService(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<Aluno> lerAlunos() {
        System.out.print("\nQuantos alunos deseja cadastrar? ");
        int quantidade = lerInteiroPositivo();

        List<Aluno> alunos = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            System.out.println("\n" + "─".repeat(50));
            System.out.printf("  Aluno %d de %d%n", i, quantidade);
            System.out.println("─".repeat(50));
            alunos.add(lerAluno());
        }
        return alunos;
    }

    // ── Leitura de um aluno

    private Aluno lerAluno() {
        String   nome  = lerNome();
        double[] notas = lerTresNotas();
        try {
            return new Aluno(nome, notas);
        } catch (NomeInvalidoException | NotaInvalidaException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            return lerAluno();
        }
    }

    // ── Leitura do nome

    private String lerNome() {
        System.out.print("  Nome do aluno: ");
        String nome = scanner.nextLine().trim();
        boolean invalido = nome.length() < 3;
        return invalido ? pedirNomeNovamente() : nome;
    }

    private String pedirNomeNovamente() {
        System.out.println("  ✘ Nome deve ter no mínimo 3 caracteres. Tente novamente.");
        return lerNome();
    }

    // ── Leitura das três notas

    private double[] lerTresNotas() {
        double[] notas = new double[3];
        for (int i = 0; i < 3; i++) {
            notas[i] = lerNota(i + 1);
        }
        return notas;
    }

    private double lerNota(int indice) {
        System.out.printf("  Nota %d (0–100): ", indice);
        try {
            double nota = Double.parseDouble(scanner.nextLine().trim());
            boolean invalida = nota < 0 || nota > 100;
            return invalida ? pedirNotaNovamente(indice) : nota;
        } catch (NumberFormatException e) {
            System.out.printf("  ✘ Entrada inválida. Nota %d deve ser um número.%n", indice);
            return lerNota(indice);
        }
    }

    private double pedirNotaNovamente(int indice) {
        System.out.printf("  ✘ Nota %d inválida. Deve ser entre 0 e 100. Tente novamente.%n", indice);
        return lerNota(indice);
    }

    // ── Utilitário

    private int lerInteiroPositivo() {
        try {
            int v = Integer.parseInt(scanner.nextLine().trim());
            boolean invalido = v <= 0;
            return invalido ? pedirQuantidadeNovamente() : v;
        } catch (NumberFormatException e) {
            System.out.print("  ✘ Valor inválido. Informe um número inteiro positivo: ");
            return lerInteiroPositivo();
        }
    }

    private int pedirQuantidadeNovamente() {
        System.out.print("  ✘ Quantidade deve ser maior que zero. Tente novamente: ");
        return lerInteiroPositivo();
    }
}
