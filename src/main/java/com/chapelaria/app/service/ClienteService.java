package com.chapelaria.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.chapelaria.app.model.Cliente;
import com.chapelaria.app.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    private final String FRONT_URL = "http://localhost:4200";

    public Cliente salvar(Cliente cliente){
        cliente.setIsAtivo(false);
        cliente.setToken(UUID.randomUUID().toString());
        Cliente salvo = repository.save(cliente);
        enviarEmailConfirmacao(salvo);

        return salvo;
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

    public Optional<Cliente> fazerLogin(String email, String senha){
        return repository.findByEmailAndSenha(email, senha);
    }

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
        String assunto = "Confirmação de cadastro da chapelaria!";
        String link = FRONT_URL + "/confirmar-conta?token=" + cliente.getToken();
        String texto = String.format("""
            Olá, %s!
            Obrigado por se cadastrar na nossa chapelaria.

            Para ativar a conta, clique no seguinte link:
            %s
        """, cliente.getNome(), link);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cliente.getEmail());
        message.setSubject(assunto);
        message.setText(texto);

        mailSender.send(message);
    }

    public void enviarEmailRedefineSenha(String email){
        Optional<Cliente> cliente = repository.findAll().stream().filter(c -> c.getEmail().equals(email)).findFirst();

        if (cliente.isPresent()) {
            cliente.get().setToken(UUID.randomUUID().toString());
            repository.save(cliente.get());

            String assunto = "Redefinição de senha da chapelaria";
            String link = FRONT_URL + "/redefinir-senha?token=" + cliente.get().getToken();

            String texto = String.format("""
                    Olá, %s

                    Para redefinir sua senha, acesse o link:
                    %s
                    """, cliente.get().getNome(), link);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(assunto);
            message.setText(texto);

            mailSender.send(message);
        }
    }

}
