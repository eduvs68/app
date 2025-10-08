package com.chapelaria.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chapelaria.app.model.Carrinho;
import com.chapelaria.app.service.CarrinhoService;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/criar/{clienteId}")
    public Carrinho criarCarrinho(@PathVariable Long clienteId) {
    	return carrinhoService.criarCarrinho(clienteId);
    }

    // POST - Adicionar item ao carrinho
    @PostMapping("/{carrinhoId}/adicionar")
    public Carrinho adicionarItem(
            @PathVariable Long carrinhoId,
            @RequestParam Long produtoId,
            @RequestParam int quantidade) {
        return carrinhoService.adicionarItem(carrinhoId, produtoId, quantidade);
    }

    @DeleteMapping("/remover/{itemId}")
    public Carrinho removerItem(@PathVariable Long itemId) {
        return carrinhoService.removerItem(itemId);
    }

    @GetMapping("/{id}")
    public Carrinho buscarCarrinho(@PathVariable Long id) {
        return carrinhoService.buscarCarrinho(id);
    }

    @DeleteMapping("/limpar/{id}")
    public Carrinho limparCarrinho(@PathVariable Long id) {
        return carrinhoService.limparCarrinho(id);
    }
}
