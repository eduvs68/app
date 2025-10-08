package com.chapelaria.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chapelaria.app.model.Carrinho;
import com.chapelaria.app.model.CarrinhoItem;
import com.chapelaria.app.model.Cliente;
import com.chapelaria.app.model.Produto;
import com.chapelaria.app.repository.CarrinhoItemRepository;
import com.chapelaria.app.repository.CarrinhoRepository;
import com.chapelaria.app.repository.ClienteRepository;
import com.chapelaria.app.repository.ProdutoRepository;

@Service
public class CarrinhoService{

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private CarrinhoItemRepository carrinhoItemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Carrinho criarCarrinho(Long clienteId){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não existe!"));
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho adicionarItem(Long carrinhoId, Long produtoId, int quantidade) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow(() -> new RuntimeException("Carrinho não existe!"));
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não existe"));

        CarrinhoItem item = new CarrinhoItem();
        item.setCarrinho(carrinho);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        carrinhoItemRepository.save(item);

        carrinho.getItens().add(item);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho removerItem(Long itemId){
        Optional<CarrinhoItem> item = carrinhoItemRepository.findById(itemId);
        if (item.isPresent()){
            Carrinho carrinho = item.get().getCarrinho();
            carrinhoItemRepository.delete(item.get());
            carrinho.getItens().remove(item.get());
            return carrinhoRepository.save(carrinho);
        }
        throw new RuntimeException("Item não encontrado");
    }

    public Carrinho buscarCarrinho(Long id){
        return carrinhoRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    public Carrinho limparCarrinho(Long id){
        Carrinho carrinho = buscarCarrinho(id);
        carrinhoItemRepository.deleteAll(carrinho.getItens());
        carrinho.getItens().clear();
        return carrinhoRepository.save(carrinho);
    }
}
