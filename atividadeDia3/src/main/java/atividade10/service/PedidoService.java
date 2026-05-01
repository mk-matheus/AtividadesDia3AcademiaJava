package atividade10.service;

import atividade10.exceptions.*;
import atividade10.model.*;
import atividade10.repository.PedidoRepository;

import java.util.List;

// clico de vida do pedido: ABERTO → RESERVED → PAID
public class PedidoService {

    private final PedidoRepository  pedidoRepository;
    private final ProdutoService     produtoService;
    private final ClienteService     clienteService;

    // Desconto automático para pedidos com 3+ itens distintos
    private static final double DESCONTO_PEDIDO_GRANDE = 0.05;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoService produtoService,
                         ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService   = produtoService;
        this.clienteService   = clienteService;
    }

    public Pedido criar(String clienteId, String[] skusEQuantidades)
            throws ClienteNaoEncontradoException, ProdutoNaoEncontradoException {

        Cliente cliente = clienteService.buscar(clienteId);
        Pedido  pedido  = new Pedido(cliente);

        for (int i = 0; i < skusEQuantidades.length - 1; i += 2) {
            String sku = skusEQuantidades[i];
            int qtd;
            try {
                qtd = Integer.parseInt(skusEQuantidades[i + 1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantidade inválida para SKU '" + sku + "'.");
            }
            Produto produto = produtoService.buscar(sku);
            pedido.adicionarItem(new ItemPedido(produto, qtd));
        }

        // desconto automático para pedidos grandes
        if (pedido.getItens().size() >= 3) {
            pedido.aplicarDesconto(DESCONTO_PEDIDO_GRANDE);
        }

        cliente.incrementarPedidos();
        pedidoRepository.salvar(pedido);
        return pedido;
    }

    /** Reserva estoque — ABERTO → RESERVED */
    public Pedido reservar(String pedidoId)
            throws PedidoNaoEncontradoException, OperacaoInvalidaException,
                   EstoqueInsuficienteException {

        Pedido pedido = buscar(pedidoId);
        validarTransicao(pedido, StatusPedido.RESERVED);

        // Reserva tudo de uma vez — se falhar em algum produto, reverte
        int i = 0;
        try {
            List<ItemPedido> itens = pedido.getItens();
            for (; i < itens.size(); i++) {
                ItemPedido item = itens.get(i);
                item.getProduto().reservar(item.getQuantidade());
            }
        } catch (EstoqueInsuficienteException e) {
            // rollback parcial
            for (int j = 0; j < i; j++) {
                ItemPedido item = pedido.getItens().get(j);
                item.getProduto().liberarEstoque(item.getQuantidade());
            }
            throw e;
        }

        pedido.setStatus(StatusPedido.RESERVED);
        return pedido;
    }

    /** Confirma pagamento — RESERVED → PAID */
    public Pedido pagar(String pedidoId)
            throws PedidoNaoEncontradoException, OperacaoInvalidaException {

        Pedido pedido = buscar(pedidoId);
        validarTransicao(pedido, StatusPedido.PAID);
        pedido.setStatus(StatusPedido.PAID);
        return pedido;
    }

    /** Falha no pagamento — RESERVED → FAILED (libera estoque) */
    public Pedido falhar(String pedidoId)
            throws PedidoNaoEncontradoException, OperacaoInvalidaException {

        Pedido pedido = buscar(pedidoId);
        validarTransicao(pedido, StatusPedido.FAILED);
        liberarEstoqueDoPedido(pedido);
        pedido.setStatus(StatusPedido.FAILED);
        return pedido;
    }

    /** Cancela pedido — ABERTO ou RESERVED → CANCELLED */
    public Pedido cancelar(String pedidoId)
            throws PedidoNaoEncontradoException, OperacaoInvalidaException {

        Pedido pedido = buscar(pedidoId);
        validarTransicao(pedido, StatusPedido.CANCELLED);

        // só libera estoque se já estava reservado
        if (pedido.getStatus() == StatusPedido.RESERVED) {
            liberarEstoqueDoPedido(pedido);
        }

        pedido.setStatus(StatusPedido.CANCELLED);
        return pedido;
    }

    public Pedido buscar(String pedidoId)
            throws PedidoNaoEncontradoException {
        return pedidoRepository.buscarPorId(pedidoId);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.listarTodos();
    }


    private void validarTransicao(Pedido pedido, StatusPedido destino)
            throws OperacaoInvalidaException {
        if (!pedido.getStatus().podeTransitarPara(destino)) {
            throw new OperacaoInvalidaException(
                String.format("Transição inválida: %s → %s para o pedido %s.",
                    pedido.getStatus(), destino, pedido.getId()));
        }
    }

    private void liberarEstoqueDoPedido(Pedido pedido) {
        pedido.getItens().forEach(item ->
            item.getProduto().liberarEstoque(item.getQuantidade()));
    }
}
