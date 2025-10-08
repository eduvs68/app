package com.chapelaria.app.repository;

import com.chapelaria.app.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByDestaqueTrue();
    List<Produto> findByNomeContainingIgnoreCase(String termo);
}
