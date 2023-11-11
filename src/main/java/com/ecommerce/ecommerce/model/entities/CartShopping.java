package com.ecommerce.ecommerce.model.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="cartShopping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartShopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "cartProduct",
            joinColumns = @JoinColumn(name = "cartShopping_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    // Otros atributos y relaciones
}
