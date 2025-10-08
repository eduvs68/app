package com.chapelaria.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chapelaria.app.model.Cliente;
import com.chapelaria.app.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        cliente.setIsAtivo(false);
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Long id,Cliente cliente) {
        Cliente ce = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não existe!"));
        ce.setNome(cliente.getNome());
        ce.setEmail(cliente.getEmail());
        ce.setSenha(cliente.getSenha());
        ce.setIsAtivo(cliente.getIsAtivo());
        return clienteRepository.save(cliente);
    }

    public void remover(Long id){
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> buscarPorCodigo(Long id){
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> fazerLogin(String email, String senha){
        return clienteRepository.findByEmailESenha(email, senha);
    }

    public boolean ativarCliente(String token){
        Optional<Cliente> cliente = clienteRepository.findByToken(token);
        if(cliente.isPresent()){
            cliente.get().setIsAtivo(true);
            clienteRepository.save(cliente.get());
            return true;
        }
        return false;
    }

    public void enviarEmailConfirmacao(Cliente cliente){

    }

    public void enviarEmailRedefineSenha(String email){

    }




}
