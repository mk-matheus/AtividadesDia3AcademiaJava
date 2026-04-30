package org.example.atividade7;

public class Main {
    public static void main(String[] args) {
        Casa casa = new Casa("Rua A, 123");
        Carro carro = new Carro("Civic");
        Arvore arvore = new Arvore("Mangueira");

        Joao joao = new Joao("João", casa, carro);

        joao.morar();
        joao.dirigir();
    }
}
