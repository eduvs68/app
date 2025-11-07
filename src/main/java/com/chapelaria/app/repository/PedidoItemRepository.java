package com.chapelaria.app.repository;

import com.chapelaria.app.model.PedidoItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long>{
    List<PedidoItem> findByPedidoId(Long pedido);
}
