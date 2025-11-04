package com.chapelaria.app.repository;

import com.chapelaria.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    Optional<Cliente> findByEmailAndSenha(String email, String senha);
    Optional<Cliente> findByToken(String token);
}