package org.example;

import java.util.Scanner;

public class Atividade2 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        char continuar;

        do {
            System.out.println("\nBem vindo(a) a calculadora!");
            System.out.print("1 - Soma\n2 - Subtração\n3 - Multiplicação\n4 - Divisão\nEscolha a operação desejada: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> System.out.println("Resultado: " + soma());
                case 2 -> System.out.println("Resultado: " + subtracao());
                case 3 -> System.out.println("Resultado: " + multiplicacao());
                case 4 -> System.out.println("Resultado: " + divisao());
                default -> System.out.println("Opção inválida.");
            }

            System.out.println("\nDeseja realizar outra operação? (s/n)");
            continuar = scanner.next().toLowerCase().charAt(0);

        } while (continuar == 's');

        System.out.println("Encerrando calculadora...");
    }

    static int soma() {
        System.out.println("Digite o primeiro número: ");
        int a = scanner.nextInt();
        System.out.println("Digite o segundo número: ");
        int b = scanner.nextInt();
        return a + b;
    }

    static int subtracao() {
        System.out.println("Digite o primeiro número: ");
        int a = scanner.nextInt();
        System.out.println("Digite o segundo número: ");
        int b = scanner.nextInt();
        return a - b;
    }

    static int multiplicacao() {
        System.out.println("Digite o primeiro número: ");
        int a = scanner.nextInt();
        System.out.println("Digite o segundo número: ");
        int b = scanner.nextInt();
        return a * b;
    }

    static int divisao() {
        System.out.println("Digite o primeiro número: ");
        int a = scanner.nextInt();
        System.out.println("Digite o segundo número: ");
        int b = scanner.nextInt();

        if (b == 0) {
            System.out.println("Divisão por zero não é permitida.");
            return 0;
        }

        return a / b;
    }
}
