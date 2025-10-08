package com.chapelaria.app.repository;

import com.chapelaria.app.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByClienteId(Long clienteId);
}