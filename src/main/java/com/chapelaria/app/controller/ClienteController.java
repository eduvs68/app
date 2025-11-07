package com.chapelaria.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chapelaria.app.model.Cliente;
import com.chapelaria.app.service.ClienteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService service;


    @GetMapping
    public List<Cliente> listar(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id){
        return service.buscarPorCodigo(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente){
        return service.salvar(cliente);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente){
        return service.alterar(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.remover(id);
    }

    @PatchMapping("/ativar/{token}")
    public ResponseEntity<String> ativarConta(@PathVariable String token){
        boolean ativado = service.ativarCliente(token);
        return ativado ? ResponseEntity.ok("Conta ativada com sucesso!"): ResponseEntity.badRequest().body("Token inválido!");
}

    @GetMapping("/redefinir-senha/{email}")
    public ResponseEntity<String> redefinirSenha(@PathVariable String email){
        service.enviarEmailRedefineSenha(email);
        return ResponseEntity.ok("E-mail de redefinição enviado, se o endereço for válido.");
    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody Map<String, String> dados){
        String email = dados.get("email");
        String senha = dados.get("senha");
        return service.fazerLogin(email, senha).map(ResponseEntity::ok).orElse(ResponseEntity.status(401).build());
    }
        

        
    
}
