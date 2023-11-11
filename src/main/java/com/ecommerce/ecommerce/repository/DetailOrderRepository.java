package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entities.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepository  extends JpaRepository<DetailOrder,Long> {
}
