package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

public interface UserRepository  extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    UserDetails findByEmail(String emailUsuario);

    boolean existsByIdentification(Number identification);

    boolean existsByIdNotAndEmail(Long id, String email);

    boolean existsByIdNotAndIdentification(Long id, BigInteger identification);
}
