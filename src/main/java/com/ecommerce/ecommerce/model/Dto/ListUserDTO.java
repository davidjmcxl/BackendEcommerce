package com.ecommerce.ecommerce.model.Dto;

import com.ecommerce.ecommerce.model.entities.User;

public record ListUserDTO(

        Long id,
        String name,

        String email,
        String address,
        Number identification,

       String rol


) {
    public ListUserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getIdentification(), user.getRol());
    }
}