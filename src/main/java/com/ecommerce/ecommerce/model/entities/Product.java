package com.ecommerce.ecommerce.model.entities;

import com.ecommerce.ecommerce.model.Dto.RegisterProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "Product")
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private String description  ;
    private BigDecimal price;
    private int  stock;
    private String date;
    private String imagen;

    public Product(RegisterProductDto registerProductDto ,String img) {
        this.name=registerProductDto.name();
        this.description=registerProductDto.description();
        this.price=registerProductDto.price();
        this.stock= registerProductDto.stock();
        this.date= registerProductDto.date();
        this.imagen=img;
    }
}
