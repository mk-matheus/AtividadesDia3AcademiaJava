package atividade10.cli;

import atividade10.exceptions.*;
import atividade10.model.*;
import atividade10.service.*;

import java.util.List;
import java.util.Scanner;

public class MenuCli {

    private final Scanner         scanner;
    private final ProdutoService  produtoService;
    private final ClienteService  clienteService;
    private final PedidoService   pedidoService;
    private final RelatorioService relatorioService;

    public MenuCli(Scanner scanner,
                   ProdutoService produtoService,
                   ClienteService clienteService,
                   PedidoService pedidoService,
                   RelatorioService relatorioService) {
        this.scanner          = scanner;
        this.produtoService   = produtoService;
        this.clienteService   = clienteService;
        this.pedidoService    = pedidoService;
        this.relatorioService = relatorioService;
    }

    public void iniciar() {
        exibirBoasVindas();
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();
            rodando = processar(opcao);
        }
        System.out.println("\nSistema encerrado. Até logo!");
    }

    // ── Menu principal ───────────────────────────────────────────────────

    private void exibirBoasVindas() {
        System.out.println("=".repeat(55));
        System.out.println("   SIMULADOR DE PROCESSAMENTO DE PEDIDOS — E-COMMERCE");
        System.out.println("=".repeat(55));
    }

    private void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("PRODUTOS");
        System.out.println("  1. Adicionar produto");
        System.out.println("  2. Listar produtos (por SKU)");
        System.out.println("  3. Listar produtos (por preço)");
        System.out.println("CLIENTES");
        System.out.println("  4. Adicionar cliente");
        System.out.println("  5. Listar clientes");
        System.out.println("PEDIDOS");
        System.out.println("  6. Criar pedido");
        System.out.println("  7. Reservar estoque");
        System.out.println("  8. Pagar pedido");
        System.out.println("  9. Falhar pagamento");
        System.out.println(" 10. Cancelar pedido");
        System.out.println(" 11. Detalhar pedido");
        System.out.println(" 12. Listar todos os pedidos");
        System.out.println("RELATÓRIOS");
        System.out.println(" 13. Faturamento total (PAID)");
        System.out.println(" 14. Top 3 produtos mais vendidos");
        System.out.println(" 15. Faturamento por categoria");
        System.out.println(" 16. Clientes mais ativos");
        System.out.println("  0. Sair");
        System.out.print("\nOpção: ");
    }

    /** Retorna false quando o usuário escolher sair. */
    private boolean processar(String opcao) {
        System.out.println();
        try {
            return switch (opcao) {
                case "1"  -> { adicionarProduto();        yield true; }
                case "2"  -> { listarProdutosPorSku();    yield true; }
                case "3"  -> { listarProdutosPorPreco();  yield true; }
                case "4"  -> { adicionarCliente();        yield true; }
                case "5"  -> { listarClientes();          yield true; }
                case "6"  -> { criarPedido();             yield true; }
                case "7"  -> { reservarEstoque();         yield true; }
                case "8"  -> { pagarPedido();             yield true; }
                case "9"  -> { falharPagamento();         yield true; }
                case "10" -> { cancelarPedido();          yield true; }
                case "11" -> { detalharPedido();          yield true; }
                case "12" -> { listarPedidos();           yield true; }
                case "13" -> { relatorioService.exibirFaturamentoTotal();         yield true; }
                case "14" -> { relatorioService.exibirTopProdutos();              yield true; }
                case "15" -> { relatorioService.exibirFaturamentoPorCategoria();  yield true; }
                case "16" -> { relatorioService.exibirClientesMaisAtivos();       yield true; }
                case "0"  -> false;
                default   -> { System.out.println("Opção inválida."); yield true; }
            };
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return true;
        }
    }

    // ── Produtos ─────────────────────────────────────────────────────────

    private void adicionarProduto() {
        System.out.print("SKU       : "); String sku       = scanner.nextLine().trim();
        System.out.print("Nome      : "); String nome      = scanner.nextLine().trim();
        System.out.print("Categoria (ELETRONICOS/VESTUARIO/ALIMENTOS/LIVROS/ESPORTES/OUTROS): ");
        String cat = scanner.nextLine().trim();
        System.out.print("Preço     : "); double preco   = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Estoque   : "); int    estoque = Integer.parseInt(scanner.nextLine().trim());

        Produto p = produtoService.adicionar(sku, nome, cat, preco, estoque);
        System.out.println("Produto adicionado: " + p);
    }

    private void listarProdutosPorSku() {
        List<Produto> lista = produtoService.listarPorSku();
        System.out.println(lista.isEmpty() ? "Nenhum produto cadastrado." : "");
        lista.forEach(System.out::println);
    }

    private void listarProdutosPorPreco() {
        List<Produto> lista = produtoService.listarPorPreco();
        System.out.println(lista.isEmpty() ? "Nenhum produto cadastrado." : "");
        lista.forEach(System.out::println);
    }

    // ── Clientes ─────────────────────────────────────────────────────────

    private void adicionarCliente() {
        System.out.print("Nome  : "); String nome  = scanner.nextLine().trim();
        System.out.print("Email : "); String email = scanner.nextLine().trim();
        Cliente c = clienteService.adicionar(nome, email);
        System.out.println("Cliente adicionado: " + c);
        System.out.println("ID gerado: " + c.getId() + "  (use este ID para criar pedidos)");
    }

    private void listarClientes() {
        List<Cliente> lista = clienteService.listarTodos();
        System.out.println(lista.isEmpty() ? "Nenhum cliente cadastrado." : "");
        lista.forEach(System.out::println);
    }

    // ── Pedidos ──────────────────────────────────────────────────────────

    private void criarPedido() throws ClienteNaoEncontradoException, ProdutoNaoEncontradoException {
        System.out.print("ID do cliente: "); String clienteId = scanner.nextLine().trim();
        System.out.println("Informe os itens no formato  SKU QUANTIDADE  (linha em branco para terminar):");

        List<String> partes = new java.util.ArrayList<>();
        while (true) {
            System.out.print("  Item: ");
            String linha = scanner.nextLine().trim();
            if (linha.isBlank()) break;
            String[] tokens = linha.split("\\s+");
            if (tokens.length < 2) {
                System.out.println("  Formato inválido. Use: SKU QUANTIDADE");
                continue;
            }
            partes.add(tokens[0]);
            partes.add(tokens[1]);
        }

        if (partes.isEmpty()) {
            System.out.println("Nenhum item informado. Pedido não criado.");
            return;
        }

        Pedido pedido = pedidoService.criar(clienteId, partes.toArray(new String[0]));
        System.out.println("Pedido criado: " + pedido.getId());
        pedido.exibirDetalhes();
    }

    private void reservarEstoque() throws PedidoNaoEncontradoException,
            OperacaoInvalidaException, EstoqueInsuficienteException {
        System.out.print("ID do pedido: ");
        Pedido p = pedidoService.reservar(scanner.nextLine().trim());
        System.out.println("Estoque reservado. Status: " + p.getStatus());
    }

    private void pagarPedido() throws PedidoNaoEncontradoException, OperacaoInvalidaException {
        System.out.print("ID do pedido: ");
        Pedido p = pedidoService.pagar(scanner.nextLine().trim());
        System.out.printf("Pedido %s pago. Total: R$ %.2f%n", p.getId(), p.getTotal());
    }

    private void falharPagamento() throws PedidoNaoEncontradoException, OperacaoInvalidaException {
        System.out.print("ID do pedido: ");
        Pedido p = pedidoService.falhar(scanner.nextLine().trim());
        System.out.println("Pagamento falhou. Estoque liberado. Status: " + p.getStatus());
    }

    private void cancelarPedido() throws PedidoNaoEncontradoException, OperacaoInvalidaException {
        System.out.print("ID do pedido: ");
        Pedido p = pedidoService.cancelar(scanner.nextLine().trim());
        System.out.println("Pedido cancelado. Status: " + p.getStatus());
    }

    private void detalharPedido() throws PedidoNaoEncontradoException {
        System.out.print("ID do pedido: ");
        pedidoService.buscar(scanner.nextLine().trim()).exibirDetalhes();
    }

    private void listarPedidos() {
        List<Pedido> lista = pedidoService.listarTodos();
        System.out.println(lista.isEmpty() ? "Nenhum pedido registrado." : "");
        lista.forEach(System.out::println);
    }
}
