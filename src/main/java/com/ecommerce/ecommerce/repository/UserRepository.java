package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
}
