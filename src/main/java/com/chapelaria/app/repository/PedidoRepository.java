package com.chapelaria.app.repository;

import com.chapelaria.app.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteCodCliente(Long codCliente);
}
