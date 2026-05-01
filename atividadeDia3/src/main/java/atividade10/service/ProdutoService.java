package atividade10.service;

import atividade10.exceptions.ProdutoNaoEncontradoException;
import atividade10.model.Categoria;
import atividade10.model.Produto;
import atividade10.repository.ProdutoRepository;

import java.util.Comparator;
import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto adicionar(String sku, String nome, String categoria,
                             double preco, int estoque) {
        if (repository.existeSku(sku))
            throw new IllegalArgumentException("SKU '" + sku + "' já cadastrado.");

        Produto p = new Produto(sku, nome, Categoria.fromString(categoria), preco, estoque);
        repository.salvar(p);
        return p;
    }

    public Produto buscar(String sku) throws ProdutoNaoEncontradoException {
        return repository.buscarPorSku(sku);
    }

    public List<Produto> listarPorSku() {
        List<Produto> lista = repository.listarTodos();
        lista.sort(Comparator.comparing(Produto::getSku));
        return lista;
    }

    public List<Produto> listarPorPreco() {
        List<Produto> lista = repository.listarTodos();
        lista.sort(Comparator.comparingDouble(Produto::getPreco));
        return lista;
    }
}
