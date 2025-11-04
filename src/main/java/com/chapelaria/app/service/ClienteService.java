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
    private ClienteRepository repository;

    public Cliente salvar(Cliente cliente){
        cliente.setIsAtivo(false);
        cliente.setToken(UUID.randomUUID().toString());

        return repository.save(cliente);
    }

    public Cliente alterar(Long id,Cliente cliente) {
        Cliente ce = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não existe!"));
        ce.setNome(cliente.getNome());
        ce.setEmail(cliente.getEmail());
        ce.setSenha(cliente.getSenha());
        ce.setIsAtivo(cliente.getIsAtivo());
        return repository.save(ce);
    }

    public void remover(Long id){
        repository.deleteById(id);
    }

    public Optional<Cliente> buscarPorCodigo(Long id){
        return repository.findById(id);
    }

    public List<Cliente> listarTodos(){
        return repository.findAll();
    }

   // public Optional<Cliente> fazerLogin(String email, String senha){
     //   return clienteRepository.findByEmailAndSenha(email, senha);
    //}

    public boolean ativarCliente(String token){
        Optional<Cliente> cliente = repository.findByToken(token);
        if(cliente.isPresent()){
            cliente.get().setIsAtivo(true);
            repository.save(cliente.get());
            return true;
        }
        return false;
    }

    public void enviarEmailConfirmacao(Cliente cliente){

    }

    public void enviarEmailRedefineSenha(String email){

    }




}
