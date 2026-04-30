package org.example.atividade3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NumerosInteiros2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //iniciando variaveis:
        int saoPares = 0;
        ArrayList<Integer> numerosPares = new ArrayList<>();

        //lendo a Quantidade de entrada:
        System.out.println();
        System.out.print("Quantos números você deseja digitar: ");
        int quantidadeDeNumeros = input.nextInt();

        //Criando vetor recebendo a entrada:
        int[] numeros = new int[quantidadeDeNumeros];

        //criando laço for para armazenar as posições e armazenar a quantidade de pares:
        for (int i = 0; i < numeros.length; i++) {
            System.out.printf("Entre com a posição %d: ", i + 1);
            numeros[i] = input.nextInt();

            //contando os pares
            saoPares += (numeros[i] % 2 == 0) ? 1 : 0;

            //pegando os valores que são pares e armazenando em uma Lista. "esse aqui é um plus":
            Object ignored = (numeros[i] % 2 == 0) ? numerosPares.add(numeros[i]) : null;
        }

        System.out.println();
        System.out.println((numeros.length == 1)
                ? "O número digitados é: " + Arrays.toString(numeros) + " "
                : "Os números digitados são: " + Arrays.toString(numeros) + " ");

        System.out.println((saoPares == 1)
                ? "A quantidade de numeros pares é " + saoPares + "."
                : "Desses números apenas " + saoPares + " são pares.");

        System.out.println((saoPares == 1)
                ? numerosPares + " é um numero par"
                : "Os numeros pares são: " + numerosPares);

        input.close();
    }
}