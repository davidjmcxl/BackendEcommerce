package com.ecommerce.ecommerce.model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigInteger;

public record UpdateUserDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,

        String password,
        @NotBlank
        String address,

        @NotNull
        BigInteger identification

) {
}
