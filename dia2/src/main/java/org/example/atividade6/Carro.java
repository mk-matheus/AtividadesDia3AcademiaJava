package org.example.atividade6;

public class Carro {
    private String marca;
    private int CodigoCor;
    private double preco;

    public Carro(String marca, int codigoCor, double preco) {
        this.marca = marca;
        CodigoCor = codigoCor;
        this.preco = preco;
    }

    public void exibir() {
        System.out.println("Marca: " + marca);
        System.out.println("Código da Cor: " + CodigoCor);
        System.out.println("Preço: R$ " + preco);
    }

    public void buzinar() {
        System.out.println("emitir som");
    }

    public void ligar(){}

    public void movimentar() {}
}
