package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository  extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    UserDetails findByEmail(String emailUsuario);
}
