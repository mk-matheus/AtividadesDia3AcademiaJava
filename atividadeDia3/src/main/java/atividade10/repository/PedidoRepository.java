package atividade10.repository;

import atividade10.exceptions.PedidoNaoEncontradoException;
import atividade10.model.Pedido;

import java.util.*;

public class PedidoRepository {

    private final Map<String, Pedido> dados = new LinkedHashMap<>();

    public void salvar(Pedido pedido) {
        dados.put(pedido.getId(), pedido);
    }

    public Pedido buscarPorId(String id) throws PedidoNaoEncontradoException {
        Pedido p = dados.get(id.trim().toUpperCase());
        if (p == null) throw new PedidoNaoEncontradoException(id);
        return p;
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(dados.values());
    }
}
