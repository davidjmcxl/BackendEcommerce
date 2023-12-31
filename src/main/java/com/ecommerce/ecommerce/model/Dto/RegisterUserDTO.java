package com.ecommerce.ecommerce.model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public record RegisterUserDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank

        String password,
        @NotBlank
        String address,

        String rol,
        @NotNull
        BigInteger identification

) {
}
