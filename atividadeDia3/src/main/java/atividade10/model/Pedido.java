package atividade10.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static int contador = 1;

    private final String            id;
    private final Cliente           cliente;
    private final List<ItemPedido>  itens;
    private final LocalDateTime     criadoEm;
    private StatusPedido            status;
    private double                  desconto;   // percentual aplicado

    public Pedido(Cliente cliente) {
        this.id       = String.format("PED-%04d", contador++);
        this.cliente  = cliente;
        this.itens    = new ArrayList<>();
        this.criadoEm = LocalDateTime.now();
        this.status   = StatusPedido.ABERTO;
        this.desconto = 0.0;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public double getSubtotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public double getTotal() {
        return getSubtotal() * (1 - desconto);
    }

    public void setStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    public void aplicarDesconto(double percentual) {
        this.desconto = Math.min(Math.max(percentual, 0), 1); // clamp [0,1]
    }

    public void exibirDetalhes() {
        System.out.println("Pedido : " + id + "  |  Status: " + status);
        System.out.println("Cliente: " + cliente.getNome() + " (" + cliente.getId() + ")");
        System.out.println("Data   : " + criadoEm.format(FMT));
        System.out.println("Itens:");
        itens.forEach(System.out::println);
        System.out.println("─".repeat(55));
        System.out.printf("  Subtotal : R$ %.2f%n", getSubtotal());
        if (desconto > 0) System.out.printf("  Desconto : %.0f%%%n", desconto * 100);
        System.out.printf("  TOTAL    : R$ %.2f%n", getTotal());
    }

    public String           getId()       { return id; }
    public Cliente          getCliente()  { return cliente; }
    public StatusPedido     getStatus()   { return status; }
    public LocalDateTime    getCriadoEm() { return criadoEm; }
    public List<ItemPedido> getItens()    { return Collections.unmodifiableList(itens); }
    public double           getDesconto() { return desconto; }

    @Override
    public String toString() {
        return String.format("%s | %-20s | %-10s | R$ %8.2f | %s",
            id, cliente.getNome(), status, getTotal(), criadoEm.format(FMT));
    }
}
