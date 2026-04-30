package org.example.atividade3;

import java.util.Scanner;

public class Eleicao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int candidatoA = 0, candidatoB = 0, brancos = 0, nulos = 0, totalVotos = 0, contadorB = 0;
        double porcentagemCandidatoA, porcentagemCandidatoB, porcentagemBrancos, porcentagemNulos;
        int voto;
        do {
            System.out.println("Escolha seu candidato ou tecle zero para encerrar\n");
            System.out.println("  1 -> Candidato A");
            System.out.println("  2 -> Candidato B");
            System.out.println("  3 -> Branco");
            System.out.println("\nQualquer número diferente de 0, 1, 2 e 3 anulará o seu voto");
            System.out.print("Digite seu voto: ");

            voto = scanner.nextInt();

            if (voto == 0) {
                System.out.println("Votação encerrada!\n");
            } else if (voto == 1) {
                candidatoA++;
            } else if (voto == 2) {
                contadorB++;
                if (contadorB % 3 != 0) {
                    candidatoB++;
                } else {
                    nulos++;
                    System.out.println("Terceiro voto no candidato B anulado!\n");
                }
            } else if (voto == 3) {
                brancos++;
            } else {
                nulos++;
            }

        } while (voto != 0);

        totalVotos = candidatoA + candidatoB + brancos + nulos;
        if (totalVotos > 0) {
            porcentagemCandidatoA = (candidatoA * 100.0) / totalVotos;
            porcentagemCandidatoB = (candidatoB * 100.0) / totalVotos;
            porcentagemBrancos = (brancos * 100.0) / totalVotos;
            porcentagemNulos = (nulos * 100.0) / totalVotos;
            System.out.println("\nTotal de votos: " + totalVotos + "\n");
            System.out.println("Candidato A: " + candidatoA + " voto(s). " + porcentagemCandidatoA + " % do total");
            System.out.println("Candidato B: " + candidatoB + " voto(s). " + porcentagemCandidatoB + " % do total");
            System.out.println("Brancos: " + brancos + " voto(s). " + porcentagemBrancos + " % do total");
            System.out.println("Nulos: " + nulos + " voto(s). " + porcentagemNulos + " % do total");
        }
    }

}
