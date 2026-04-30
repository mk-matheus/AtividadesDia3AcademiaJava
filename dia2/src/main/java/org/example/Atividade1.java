package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Atividade1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int qtdBilhetesA;
        int qtdBilhetesB;
        int qtdBilhetesC;

        System.out.print("Quantos bilhetes foram vendidos da classe A? ");
        qtdBilhetesA = scanner.nextInt();
        System.out.print("Quantos bilhetes foram vendidos da classe B? ");
        qtdBilhetesB = scanner.nextInt();
        System.out.print("Quantos bilhetes foram vendidos da classe C? ");
        qtdBilhetesC = scanner.nextInt();

        int rendaTotal = (qtdBilhetesA * 50) + (qtdBilhetesB * 30) + (qtdBilhetesC * 20);
        System.out.println("A renda gerada pela venda dos engressos ao Estágio foi de: R$ " + rendaTotal);
    }
}