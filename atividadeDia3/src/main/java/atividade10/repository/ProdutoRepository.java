package atividade10.repository;

import atividade10.exceptions.ProdutoNaoEncontradoException;
import atividade10.model.Produto;

import java.util.*;

public class ProdutoRepository {

    private final Map<String, Produto> dados = new LinkedHashMap<>();

    public void salvar(Produto produto) {
        dados.put(produto.getSku(), produto);
    }

    public Produto buscarPorSku(String sku) throws ProdutoNaoEncontradoException {
        Produto p = dados.get(sku.toUpperCase().trim());
        if (p == null) throw new ProdutoNaoEncontradoException(sku);
        return p;
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    public boolean existeSku(String sku) {
        return dados.containsKey(sku.toUpperCase().trim());
    }
}
