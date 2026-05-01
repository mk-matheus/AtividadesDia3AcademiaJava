package atividade10.service;

import atividade10.model.*;
import atividade10.repository.PedidoRepository;

import java.util.*;
import java.util.stream.Collectors;

public class RelatorioService {

    private final PedidoRepository pedidoRepository;

    public RelatorioService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void exibirFaturamentoTotal() {
        List<Pedido> pagos = filtrarPorStatus(StatusPedido.PAID);
        double total = pagos.stream().mapToDouble(Pedido::getTotal).sum();

        System.out.println("Faturamento Total (pedidos PAID)");
        System.out.println("─".repeat(40));
        System.out.printf("  Pedidos pagos : %d%n", pagos.size());
        System.out.printf("  Total geral   : R$ %.2f%n", total);
    }

    public void exibirTopProdutos() {
        Map<String, long[]> totais = new LinkedHashMap<>();
        // [0] = quantidade total, [1] = faturamento (em centavos para evitar double map)

        filtrarPorStatus(StatusPedido.PAID).stream()
            .flatMap(p -> p.getItens().stream())
            .forEach(item -> {
                String sku = item.getProduto().getSku();
                totais.computeIfAbsent(sku, k -> new long[]{0, 0});
                totais.get(sku)[0] += item.getQuantidade();
            });

        System.out.println("Top 3 Produtos Mais Vendidos (PAID)");
        System.out.println("─".repeat(40));

        totais.entrySet().stream()
            .sorted((a, b) -> Long.compare(b.getValue()[0], a.getValue()[0]))
            .limit(3)
            .forEach(e -> System.out.printf("  %-10s  %d unidades%n",
                e.getKey(), e.getValue()[0]));

        if (totais.isEmpty()) System.out.println("  Nenhum pedido PAID registrado.");
    }

    public void exibirFaturamentoPorCategoria() {
        Map<Categoria, Double> porCategoria = new TreeMap<>(Comparator.comparing(Enum::name));

        filtrarPorStatus(StatusPedido.PAID).stream()
            .flatMap(p -> p.getItens().stream())
            .forEach(item -> porCategoria.merge(
                item.getProduto().getCategoria(),
                item.getSubtotal(),
                Double::sum
            ));

        System.out.println("Faturamento por Categoria (PAID)");
        System.out.println("─".repeat(40));

        if (porCategoria.isEmpty()) {
            System.out.println("  Nenhum pedido PAID registrado.");
            return;
        }

        porCategoria.forEach((cat, valor) ->
            System.out.printf("  %-15s R$ %.2f%n", cat, valor));
    }

    public void exibirClientesMaisAtivos() {
        Map<String, Integer> contagem = new LinkedHashMap<>();

        pedidoRepository.listarTodos().forEach(p ->
            contagem.merge(
                p.getCliente().getNome() + " (" + p.getCliente().getId() + ")",
                1, Integer::sum
            ));

        System.out.println("Clientes com Maior Número de Pedidos");
        System.out.println("─".repeat(40));

        if (contagem.isEmpty()) {
            System.out.println("  Nenhum pedido registrado.");
            return;
        }

        contagem.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .forEach(e -> System.out.printf("  %-35s %d pedido(s)%n",
                e.getKey(), e.getValue()));
    }

    private List<Pedido> filtrarPorStatus(StatusPedido status) {
        return pedidoRepository.listarTodos().stream()
            .filter(p -> p.getStatus() == status)
            .collect(Collectors.toList());
    }
}
