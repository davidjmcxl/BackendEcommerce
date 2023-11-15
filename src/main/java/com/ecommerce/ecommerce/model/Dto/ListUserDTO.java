package com.ecommerce.ecommerce.model.Dto;

import com.ecommerce.ecommerce.model.entities.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ListUserDTO(

        Long id,
        String name,

        String email,
        String address,
        BigInteger identification,

       String rol


) {
    public ListUserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getIdentification(), user.getRol());
    }


}