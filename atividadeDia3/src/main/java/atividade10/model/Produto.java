package atividade10.model;

import atividade10.exceptions.EstoqueInsuficienteException;

public class Produto {

    private final String    sku;
    private final String    nome;
    private final Categoria categoria;
    private final double    preco;
    private int             estoque;

    public Produto(String sku, String nome, Categoria categoria, double preco, int estoque) {
        validar(sku, nome, preco, estoque);
        this.sku       = sku.toUpperCase().trim();
        this.nome      = nome.trim();
        this.categoria = categoria;
        this.preco     = preco;
        this.estoque   = estoque;
    }

    // Operações de estoque

    /** Reserva (decrementa) estoque para um pedido. */
    public void reservar (int quantidade) throws EstoqueInsuficienteException {
        if (quantidade > estoque)
            throw new EstoqueInsuficienteException(sku, quantidade, estoque);
        estoque -= quantidade;
    }

    /** Devolve estoque ao cancelar/falhar um pedido. */
    public void liberarEstoque(int quantidade) {
        estoque += quantidade;
    }

    // Validação

    private static void validar(String sku, String nome, double preco, int estoque) {
        if (sku == null || sku.isBlank())     throw new IllegalArgumentException("SKU não pode ser vazio.");
        if (nome == null || nome.isBlank())   throw new IllegalArgumentException("Nome não pode ser vazio.");
        if (preco < 0)                        throw new IllegalArgumentException("Preço não pode ser negativo.");
        if (estoque < 0)                      throw new IllegalArgumentException("Estoque não pode ser negativo.");
    }

    // ── Getters

    public String    getSku()       { return sku; }
    public String    getNome()      { return nome; }
    public Categoria getCategoria() { return categoria; }
    public double    getPreco()     { return preco; }
    public int       getEstoque()   { return estoque; }

    @Override
    public String toString() {
        return String.format("[%-10s] %-25s | %s | R$ %8.2f | Estoque: %d",
            sku, nome, categoria, preco, estoque);
    }
}
