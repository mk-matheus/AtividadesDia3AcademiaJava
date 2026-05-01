package atividade10.repository;

import atividade10.exceptions.ClienteNaoEncontradoException;
import atividade10.model.Cliente;

import java.util.*;

public class ClienteRepository {

    private final Map<String, Cliente> dados = new LinkedHashMap<>();

    public void salvar(Cliente cliente) {
        dados.put(cliente.getId(), cliente);
    }

    public Cliente buscarPorId(String id) throws ClienteNaoEncontradoException {
        Cliente c = dados.get(id.trim());
        if (c == null) throw new ClienteNaoEncontradoException(id);
        return c;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(dados.values());
    }
}
