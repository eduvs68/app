package com.chapelaria.app.model;

import com.chapelaria.app.Enum.Categoria;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;
    private Boolean destaque;
    private Categoria categoria;
}

