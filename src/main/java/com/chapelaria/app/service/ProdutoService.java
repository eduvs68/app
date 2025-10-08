package com.chapelaria.app.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chapelaria.app.model.Produto;
import com.chapelaria.app.repository.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto alterar(Long id, Produto produto){
        Produto pe = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não existe!"));
        pe.setNome(produto.getNome());
        pe.setPreco(produto.getPreco());
        pe.setDescricao(produto.getDescricao());
        pe.setEstoque(produto.getEstoque());
        pe.setCategoria(produto.getCategoria());
        pe.setDestaque(produto.getDestaque());
        return produtoRepository.save(produto);
    }

    public void remover(Long id){
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> buscarPorCodigo(Long id){
        return produtoRepository.findById(id);
    }

    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public List<Produto> listarVitrine(){
        return produtoRepository.findByDestaqueTrue();
    }

    public List<Produto> buscarPorTermo(String termo){
        return produtoRepository.findByNomeContainingIgnoreCase(termo);
    }
}
