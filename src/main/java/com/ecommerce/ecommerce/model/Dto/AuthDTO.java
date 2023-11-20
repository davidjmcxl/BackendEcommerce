package com.ecommerce.ecommerce.model.Dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
){
}
