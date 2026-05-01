package atividade10.service;

import atividade10.exceptions.ClienteNaoEncontradoException;
import atividade10.model.Cliente;
import atividade10.repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente adicionar(String nome, String email) {
        Cliente c = new Cliente(nome, email);
        repository.salvar(c);
        return c;
    }

    public Cliente buscar(String id) throws ClienteNaoEncontradoException {
        return repository.buscarPorId(id);
    }

    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }
}
