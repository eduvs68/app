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

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private CarrinhoItemRepository carrinhoItemRepository;

    public Carrinho criaCarrinho(Cliente cliente){
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho adicionarItem(Carrinho carrinho, Produto produto, int quantidade){
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
            return carrinhoRepository.save(carrinho);
        }
        return null;
    }

    public Carrinho buscarCarrinho(Long id){
        return carrinhoRepository.findById(id).orElse(null);
    }

    public void limparCarrinho(Long id){
        Carrinho carrinho = buscarCarrinho(id);
        if (carrinho != null){
            carrinhoItemRepository.deleteAll(carrinho.getItens());
            carrinho.getItens().clear();
            carrinhoRepository.save(carrinho);
        }
    }
    
}
