package atividade10;

import atividade10.cli.MenuCli;
import atividade10.repository.*;
import atividade10.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Repositórios
        ProdutoRepository  produtoRepo  = new ProdutoRepository();
        ClienteRepository  clienteRepo  = new ClienteRepository();
        PedidoRepository   pedidoRepo   = new PedidoRepository();

        // Services
        ProdutoService   produtoService   = new ProdutoService(produtoRepo);
        ClienteService   clienteService   = new ClienteService(clienteRepo);
        PedidoService    pedidoService    = new PedidoService(pedidoRepo, produtoService, clienteService);
        RelatorioService relatorioService = new RelatorioService(pedidoRepo);

        // CLI
        Scanner scanner = new Scanner(System.in);
        MenuCli menu = new MenuCli(scanner, produtoService, clienteService,
                                   pedidoService, relatorioService);
        menu.iniciar();
        scanner.close();
    }
}
