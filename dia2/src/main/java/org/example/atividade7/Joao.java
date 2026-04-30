package org.example.atividade7;

public class Joao {
    String nome;
    Casa casa;
    Carro carro;

    public Joao (String nome, Casa casa, Carro carro) {
        this.nome = nome;
        this.casa = casa;
        this.carro = carro;
    }

    void morar() {
        System.out.println(nome + " mora na casa em " + casa.endereco);
    }

    void dirigir() {
        System.out.println(nome + " dirige um " + carro.modelo);
    }
}