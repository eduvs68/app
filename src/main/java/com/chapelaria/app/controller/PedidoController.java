package com.chapelaria.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chapelaria.app.model.Pedido;
import com.chapelaria.app.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping
    public List<Pedido> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id){
    return service.buscarPorId(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
    }

    @GetMapping("/clientes/{clienteId}")
    public List<Pedido> listarPorCliente(@PathVariable Long clienteId){
        return service.listarPorCliente(clienteId);
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido){
        return service.criar(pedido);
    }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @RequestBody Pedido pedido){
        return service.atualizar(id, pedido);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
    
}
