package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entities.CartShopping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShoppingRepository extends JpaRepository<CartShopping,Long> {
}
