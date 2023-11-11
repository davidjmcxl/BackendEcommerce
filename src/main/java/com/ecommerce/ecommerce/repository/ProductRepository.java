package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
}
