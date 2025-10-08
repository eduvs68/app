package com.chapelaria.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String remetente = "eduvs68@gmail.com";

    public void enviarEmail(String destinatario, String assunto, String mensagem) {
	SimpleMailMessage email = new SimpleMailMessage();
       	email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom(remetente);
        mailSender.send(email);
    }

    public void enviarEmailConfirm(String destinatario, String nome, Long clienteId) {
        String assunto = "Confirmação de Cadastro - Online Bombetas Chapelaria LTDA.";
        String linkAtivacao = "http://localhost:8080/clientes/ativar/" + clienteId;
        String mensagem = """ Olá %s!
                Seja bem-vindo à Online Bombetas
                Para ativar sua conta, clique no link abaixo:
                %s""".formatted(nome, linkAtivacao);
        enviarEmail(destinatario, assunto, mensagem);
    }

    public void enviarEmailRedefineSenha(String destinatario, Long clienteId) {
        String assunto = "Redefinição de Senha";
        String linkRedefinicao = "http://localhost:8080/clientes/redefinir/" + clienteId;
        String mensagem = """ Olá!
                Recebemos uma solicitação para redefinir sua senha.
                Para redefinir, clique no link abaixo:
                %s
                Caso não tenha solicitado, ignore este e-mail.""".formatted(linkRedefinicao);
        enviarEmail(destinatario, assunto, mensagem);
    }

    public void enviarEmailConfirmPedido(String destinatario, Long pedidoId) {
        String assunto = "Confirmação de Pedido";
        String mensagem = """Olá!
                Seu pedido nº %d foi recebido com sucesso 🎉
                Em breve entraremos em contato com mais detalhes sobre o envio.
                Obrigado por comprar conosco!
                - Equipe Online Bombetas""".formatted(pedidoId);
        enviarEmail(destinatario, assunto, mensagem);
    }
}
