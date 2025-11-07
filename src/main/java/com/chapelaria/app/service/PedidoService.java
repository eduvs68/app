package com.chapelaria.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chapelaria.app.model.Cliente;
import com.chapelaria.app.model.Pedido;
import com.chapelaria.app.model.PedidoItem;
import com.chapelaria.app.model.Produto;
import com.chapelaria.app.repository.ClienteRepository;
import com.chapelaria.app.repository.PedidoItemRepository;
import com.chapelaria.app.repository.PedidoRepository;
import com.chapelaria.app.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    @Autowired
    private PedidoRepository repository;
    @Autowired
    private PedidoItemRepository pedidoItemRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido criar(Pedido pedido){
        Cliente cliente = clienteRepository.findById(pedido.getCliente().getCodCliente()).orElseThrow(() -> new RuntimeException("Cliente não Encontrado!"));
        Pedido pedidoSalvo = repository.save(pedido);

        for (PedidoItem item : pedido.getItens()){
            Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
            item.setProduto(produto);
            item.setPedido(pedidoSalvo);
            pedidoItemRepository.save(item);
        }
        pedidoSalvo.setItens(pedidoItemRepository.findByPedidoId(pedidoSalvo.getId()));
        return pedidoSalvo;
    }

    public Pedido atualizar(Long id, Pedido pedidoAt){
        Pedido pedidoEx = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
        pedidoEx.setStatus(pedidoAt.getStatus());
        return repository.save(pedidoEx);
    }

    public void deletar(Long id){
        Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        List<PedidoItem> itens = pedidoItemRepository.findByPedidoId(id);
        pedidoItemRepository.deleteAll(itens);
    }

    public Optional<Pedido> buscarPorId(Long id){
        return repository.findById(id);
    }

    public List<Pedido> listarTodos(){
        return repository.findAll();
    }

    public List<Pedido> listarPorCliente(Long codCliente){
        return repository.findByClienteCodCliente(codCliente);
    }

}
